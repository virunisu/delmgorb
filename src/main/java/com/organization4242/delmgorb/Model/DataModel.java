package com.organization4242.delmgorb.Model;

import org.apache.commons.math3.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math3.ode.FirstOrderIntegrator;
import org.apache.commons.math3.ode.nonstiff.*;

import java.util.ArrayList;

import static java.lang.Math.*;
import static java.lang.System.*;

public class DataModel {
    private ArrayList<Point3D> listOfPoints;
    {
        listOfPoints = new ArrayList<Point3D>();
    }
    private ThreeArrays threeArrays;
    {
        threeArrays = new ThreeArrays();
    }

    public ArrayList<Point3D> getListOfPoints() {
        return listOfPoints;
    }

    public ThreeArrays getThreeArrays() {return threeArrays;}

    public DataModel(int num, double time_step, IntegrationMethods method,
                     double xMin, double xMax, double yMin, double yMax) {
        //Here listOfPoints gets assigned
        double angle = PI/20;
        //listOfPoints = buildPoints(num, 100, time_step, angle, angle, angle, method);
        threeArrays = buildNewPoints(num, 100, time_step, angle, angle, angle, method, xMin, xMax, yMin, yMax);
    }

    private ThreeArrays buildNewPoints (int num_of_points, double time, double time_step,
                                        double phi_0, double theta_0, double psi_0, IntegrationMethods method,
                                        double xMin, double xMax, double yMin, double yMax) {
        out.println("Inside buildNewPoints");
        ThreeArrays combo_array;
        combo_array = new ThreeArrays(num_of_points, num_of_points);
        FirstOrderIntegrator integrator = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);
        switch (method) {
            case Euler: integrator = new EulerIntegrator(0.01); break;
            case Midpoint: integrator = new MidpointIntegrator(0.05); break;
            case ClassicalRungeKutta: integrator = new ClassicalRungeKuttaIntegrator(0.1); break;
            case Gill: integrator = new GillIntegrator(0.1); break;
            case ThreeEights: integrator = new ThreeEighthesIntegrator(0.05); break;
            case HighamAndHall: integrator = new HighamHall54Integrator(0.05, 0.1, 1.0, 0.5);break;
            case DormandPrince5: integrator = new DormandPrince54Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);break;
            case DormandPrince8: integrator = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10); break;
            case GraggBulirschStoer: integrator = new GraggBulirschStoerIntegrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10); break;
            case AdamsBashforth: integrator = new AdamsBashforthIntegrator(3, 0.01, 0.05, 1.0, 0.5); break;
            case AdamsMoulton: integrator = new AdamsMoultonIntegrator(2, 0.01, 0.05, 1.0, 0.5); break;
        }

        /*вычисляем начальные условия. на входе они в самолетных углах, а нужны в кватернионах*/

        double S_ph0;
        S_ph0 = sin(phi_0 / 2);
        double S_ps0;
        S_ps0 = sin(psi_0 / 2);
        double S_th0;
        S_th0 = sin(theta_0 / 2);
        double C_ph0;
        C_ph0 = cos(phi_0 / 2);
        double C_ps0;
        C_ps0 = cos(psi_0 / 2);
        double C_th0;
        C_th0 = cos(theta_0 / 2);

        double lambda_0;
        lambda_0 = C_ph0*C_ps0*C_th0 + S_ph0*S_ps0*S_th0;
        double lambda_1;
        lambda_1 = S_ph0*C_ps0*C_th0 - C_ph0*S_ps0*S_th0;
        double lambda_2;
        lambda_2 = C_ph0*C_ps0*S_th0 + S_ph0*S_ps0*C_th0;
        double lambda_3;
        lambda_3 = C_ph0*S_ps0*C_th0 - S_ph0*C_ps0*S_th0;

        /*строим разбиение треугольника Белецкого (по сути плоскость параметров эпсилон-дельта) на точки*/

        double eps = 0;
        double del = 0;
        for (int i = 0; i < num_of_points; i++) {
            eps = yMin + 1.0 * i * (yMax - yMin) / (num_of_points - 1);
            combo_array.y_val[i] = eps;
            System.out.println("y_val = " + combo_array.y_val[i]);
            }
        for (int j = 0; j < num_of_points; j++) {
            del = xMin + 1.0 * j * (xMax - xMin) / (num_of_points - 1);
            combo_array.x_val[j] = del;
            System.out.println("x_val = " + combo_array.x_val[j]);
        }
        System.out.println("xMin = " + xMin);
        System.out.println("xMax = " + xMax);
        System.out.println("yMin = " + yMin);
        System.out.println("yMax = " + yMax);
        /*for (int i = 1; i <= num_of_points; i++) {
            eps = 1.0 * i / (num_of_points);
            //eps = i;
            combo_array.y_val[i-1] = eps;
            if(combo_array.y_val[i-1]==0) combo_array.y_val[i] = 0.000001;
        }
        for (int j = 0; j < num_of_points; j++) {
            del = 1 + 1.0 * j / (num_of_points - 1);
            //del = j;
            combo_array.x_val[j] = del;
        }*/
        out.print(combo_array.x_val[0]);out.print(" ");out.println(combo_array.x_val[num_of_points-1]);
        out.print(combo_array.y_val[0]);out.print(" ");out.println(combo_array.y_val[num_of_points-1]);
        out.println("Check!");

        for (int i = 0; i < num_of_points; i++) {
            for (int j = 0; j < num_of_points; j++) {
                double max;
                max = 0;
                for (int t = 1; t <= time/time_step; t++) {
                    double[] y0; // initial state
                    y0 = new double[] { lambda_0, lambda_1, lambda_2, lambda_3, 0, 1, 0 };
                    double[] y1; // final state
                    y1 = new double[] { 0, 0, 0, 0, 0, 0, 0 };
                    double time_state;
                    time_state = 1.0*t*time_step;
                    //out.println(combo_array.y_val[i]);
                    //out.println(combo_array.x_val[j]);
                    FirstOrderDifferentialEquations ode = new LibrationODE(1000, combo_array.y_val[i],
                            combo_array.x_val[j], 0.001078011072);
                    integrator.integrate(ode, 0.0, y0, time_state, y1);// now y1 contains final state at time t/100
                    double alpha_1;//элемент матрицы направляющих косинусов
                    alpha_1 = y1[0]*y1[0] + y1[1]*y1[1] - y1[2]*y1[2] - y1[3]*y1[3];
                    double beta_1;//элемент матрицы направляющих косинусов
                    beta_1 = 2*(y1[1]*y1[2] + y1[0]*y1[3]);
                    double psi;//вычисляем самолетный угол
                    psi = atan(beta_1 / alpha_1);
                    if (psi >= max) max = psi;
                }
                combo_array.f_val[j][i] = max;
                out.print("eps = ");out.print(combo_array.y_val[i]);
                out.print(" del = ");out.print(combo_array.x_val[j]);
                out.print(" val = ");out.println(combo_array.f_val[j][i]);
            }
        }
        out.println("Outside buildNewPoints");
        return combo_array;
    }

    private ArrayList<Point3D> buildPoints(int num_of_points, double time, double time_step,
                                           double phi_0, double theta_0, double psi_0, IntegrationMethods method) {

        out.println("Inside buildPoints");
        ArrayList<Point3D> list;
        list = new ArrayList<Point3D>();
        out.println(method);
        FirstOrderIntegrator integrator = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);
        switch (method) {
            case Euler: integrator = new EulerIntegrator(0.01); break;
            case Midpoint: integrator = new MidpointIntegrator(0.05); break;
            case ClassicalRungeKutta: integrator = new ClassicalRungeKuttaIntegrator(0.1); break;
            case Gill: integrator = new GillIntegrator(0.1); break;
            case ThreeEights: integrator = new ThreeEighthesIntegrator(0.05); break;
            case HighamAndHall: integrator = new HighamHall54Integrator(0.05, 0.1, 1.0, 0.5);break;
            case DormandPrince5: integrator = new DormandPrince54Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10);break;
            case DormandPrince8: integrator = new DormandPrince853Integrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10); break;
            case GraggBulirschStoer: integrator = new GraggBulirschStoerIntegrator(1.0e-8, 100.0, 1.0e-10, 1.0e-10); break;
            case AdamsBashforth: integrator = new AdamsBashforthIntegrator(3, 0.01, 0.05, 1.0, 0.5); break;
            case AdamsMoulton: integrator = new AdamsMoultonIntegrator(2, 0.01, 0.05, 1.0, 0.5); break;
        }

        /*вычисляем начальные условия. на входе они в самолетных углах, а нужны в кватернионах*/

        double S_ph0;
        S_ph0 = sin(phi_0 / 2);
        double S_ps0;
        S_ps0 = sin(psi_0 / 2);
        double S_th0;
        S_th0 = sin(theta_0 / 2);
        double C_ph0;
        C_ph0 = cos(phi_0 / 2);
        double C_ps0;
        C_ps0 = cos(psi_0 / 2);
        double C_th0;
        C_th0 = cos(theta_0 / 2);

        double lambda_0;
        lambda_0 = C_ph0*C_ps0*C_th0 + S_ph0*S_ps0*S_th0;
        double lambda_1;
        lambda_1 = S_ph0*C_ps0*C_th0 - C_ph0*S_ps0*S_th0;
        double lambda_2;
        lambda_2 = C_ph0*C_ps0*S_th0 + S_ph0*S_ps0*C_th0;
        double lambda_3;
        lambda_3 = C_ph0*S_ps0*C_th0 - S_ph0*C_ps0*S_th0;

        /*строим разбиение треугольника Белецкого (по сути плоскость параметров эпсилон-дельта) на точки*/

        int counter = 0;
        double eps = 0;
        double del = 0;
        for (int i = 1; i <= num_of_points; i++) {
            eps = 1.0 * i / (num_of_points + 1);
            for (int j = 1; j <= num_of_points; j++) {
                del = 1 + 1.0 * j / (num_of_points + 1);
                if(eps - del > -1) {
                    list.add(counter, new Point3D(del, eps, 0));
                    counter++;
                }
            }
        }

        /*в каждой точке выполняем численное интегрирование для разного времени, ищем максимум самолетного угла*/
        for (int i = 0; i <= counter - 1; i++) {
            double max;
            max = 0;
            for (int t = 1; t <= time/time_step; t++) {
                double[] y0; // initial state
                y0 = new double[] { lambda_0, lambda_1, lambda_2, lambda_3, 0, 1, 0 };
                double[] y1; // final state
                y1 = new double[] { 0, 0, 0, 0, 0, 0, 0 };
                double time_state;
                time_state = 1.0*t*time_step;
                FirstOrderDifferentialEquations ode = new LibrationODE(1000, list.get(i).y, list.get(i).x, 0.001078011072);
                integrator.integrate(ode, 0.0, y0, time_state, y1);// now y1 contains final state at time t/100
                double alpha_1;//элемент матрицы направляющих косинусов
                alpha_1 = y1[0]*y1[0] + y1[1]*y1[1] - y1[2]*y1[2] - y1[3]*y1[3];
                double beta_1;//элемент матрицы направляющих косинусов
                beta_1 = 2*(y1[1]*y1[2] + y1[0]*y1[3]);
                double psi;//вычисляем самолетный угол
                psi = atan(beta_1 / alpha_1);
                /*out.println("________");
                out.print("i: ");     out.print(i);
                out.print(" t: ");    out.print(t);
                out.print(" y[0]: "); out.println(y1[0]);*/
                if (psi >= max) max = psi;
            }
            double epsilon = list.get(i).y;
            double delta  = list.get(i).x;
            list.remove(i);
            list.add(i, new Point3D(delta, epsilon, max));
            System.out.println(list.get(i));
        }
        out.println("Outside buildPoints");
        return list;
    }
}
