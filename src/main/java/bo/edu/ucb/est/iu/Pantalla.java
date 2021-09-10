/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.ucb.est.iu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Pantalla cre = new Pantalla("Men� principal");
 * cre.agregarContenido(" 1. Versaldo" , "2. Retirar fondos", "3. Depositar fondos")
 * cre.definirDatoEntrada("Elija una opci�n: ",  "Integer");
 * 
 * Pantalla login = new Pantalla("Inreso al sistema");
 * login.agregarContenido(" Bienvenido al Banco")
 * login.definirDatoEntrada("Ingrese su codigo de usuario", "String");
 * login.definirDatoEntrada("Ingrese su pin", "String");
 * 
 * List Object variables = login.ejecutaPantalla();
 * 
 * @author ecampohermoso
 */
public class Pantalla {
    private String titulo;
    private List<String> contenido;
    
    // Para los datos que se le solicitar� al cliente
    private List<String> etiquetasIn;
    private List<String> tipoDatoIn;
    private List<Object> valores;
    

    public Pantalla(String titulo) {
        this.titulo = titulo;
        this.contenido = new ArrayList();
        this.etiquetasIn = new ArrayList();
        this.tipoDatoIn = new ArrayList();
        this.valores = new ArrayList();
    }
    
    public void definirDatoEntrada(String etiqueta, String tipoDato) {
        this.etiquetasIn.add(etiqueta);
        this.tipoDatoIn.add(tipoDato);
    }

    public void setContenido(List<String> contenido) {
        this.contenido = contenido;
    }
    
    public List<Object> desplegar() {
        System.out.println("");
        System.out.println("**********************************************");
        System.out.println(this.titulo);
        System.out.println("**********************************************");
        System.out.println("");
        for (int i = 0; i < contenido.size(); i++) {
            System.out.println(contenido.get(i));
        }
        System.out.println("");
        
        // Procedemos a pedir los datos
        Scanner sc = new Scanner(System.in);
        for (int i = 0; i < etiquetasIn.size(); i++) {
            System.out.println(etiquetasIn.get(i));
            if (tipoDatoIn.get(i).equals("String")) {
                valores.add(sc.nextLine());
            } else if (tipoDatoIn.get(i).equals("Integer")) {
                valores.add(sc.nextInt());
            }
        }
        
       // Pedir los datos de entrada.
       return valores;
        
    }

    public String getTitulo() {
        return titulo;
    }
    
    
        
}
