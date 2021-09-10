/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.edu.ucb.est.modelo;

import bo.edu.ucb.est.iu.Pantalla;
//import jdk.internal.jshell.tool.resources.l10n;
import jdk.javadoc.internal.doclets.formats.html.SourceToHTMLConverter;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ecampohermoso
 */
public class Cajero {
    
    
    private Pantalla pantallaError;
    private Pantalla pantallaRealizarDeposito;
    private Pantalla pantallaRealizarRetiro;
    private Banco banco;
    private Cliente cliente;
    

    public Cajero(Banco banco) {
        this.banco = banco;
        
        // Las siguientes son pantallas dinamicas, es decir su contenido
        // va a cambiar con el comportamiento del programa
        
        // Iniciamos pantalla de error.
        pantallaError = new Pantalla("Ocurrio un error");        
        // Corremos primera pantalla
        
    }
    
    public void iniciarCajero() {
        boolean salir = false;
        while(!salir) {
            Pantalla resultado = null;
            // Verificar si es que hay un cliente con la sesión activa
            if(cliente != null) {
                //Mostrar la pantalla principal
                resultado = construirPantallaPrincipal();
            } else {
                //Mostrar la pantalla de ingreso
                Pantalla pantallaIngreso = construirPantallaIngreso();
                List<Object> credenciales = pantallaIngreso.desplegar(); // Obtenemos las credenciales
                
                // Puede retornar pantalla de error o menu de opciones
               resultado = controladorValidarCredenciales(credenciales);
            }

            //Verificar el titulo de la pantalla
            if (resultado.getTitulo().equals("Cajero ATM")) {
                //Se muestra el menu de opciones
                List<Object> opcionListado = resultado.desplegar();
                Integer opcion = (Integer) opcionListado.get(0);
                if (opcion == 1) { // Ver saldo
                    verSaldo();
                } else if (opcion == 2){ // Depositar
                    depositar();
                } else if (opcion == 3) { // Retirar
                    retirar();
                } else {
                    cliente = null;
                }
            } else {
                // Es error y se muestra el mensaje de error
                resultado.desplegar();
            }
        }
    }
    
 
    
    /**
     * Este metodo valida las credenciales ingresadas por el usuario, entonces
     * existen opciones.
     *  1. Las credenciales sean v�lidas.: Retorna la pantalla de men� principal
     *  2. LAs credenciales sean inv�lidas: Retorna la pantalla de error
     * @param credenciales
     * @return 
     */
    private Pantalla controladorValidarCredenciales(List<Object> credenciales) {
        Pantalla resultado = null;
        cliente = banco.buscarClientePorCodigo( (String) credenciales.get(0), 
                (String) credenciales.get(1));
        if (cliente == null) { // Significa que las credenciales son incorrectas
            List contenido = new ArrayList();
            contenido.add("No se encontr� al usuario.");
            pantallaError.setContenido(contenido);
            pantallaError.desplegar();
            resultado = pantallaError;
        } else {
            resultado = construirPantallaPrincipal();
        }
        return resultado;
    }
    
    private Pantalla construirPantallaIngreso() {
        // Inicializaci�n de pantallas y configuraci�n.
        Pantalla pantallaIngreso = new Pantalla("Cajero autom�tico");
        List ingresoContenido = new ArrayList();
        ingresoContenido.add(" Bienvenido al sistema, por favor ingrese su credenciales");
        pantallaIngreso.setContenido(ingresoContenido);
        pantallaIngreso.definirDatoEntrada("C�digo de usuario: ", "String");
        pantallaIngreso.definirDatoEntrada("PIN: ", "String");
        return pantallaIngreso;
    }
    
    private Pantalla construirPantallaPrincipal() {
        Pantalla pantallaMenuPrincipal  = new Pantalla("Cajero ATM");
        List menuPrincipalContenido = new ArrayList();
        menuPrincipalContenido.add(" Elija una de las siguientes opciones:");
        menuPrincipalContenido.add(" 1. Ver saldo.");
        menuPrincipalContenido.add(" 2. Retirar dinero.");
        menuPrincipalContenido.add(" 3. Depositar dinero.");
        menuPrincipalContenido.add(" 4. Salir");
        menuPrincipalContenido.add(" ");
        pantallaMenuPrincipal.setContenido(menuPrincipalContenido);
        pantallaMenuPrincipal.definirDatoEntrada("Seleccione una opci�n: ", "Integer");
       return pantallaMenuPrincipal;
    }
    
    private void verSaldo() {
        Pantalla pantallaListadoCuentas = construirPantallaListadoCuentas();
        
         List<Object> datosIntroducidos = pantallaListadoCuentas.desplegar(); // Retorna la cuenta que eligi�
         Integer indiceCuenta = (Integer) datosIntroducidos.get(0);
         //TODO validar que el indiceCuenta sea un numero entre 1 y el numero total de cuentas
         // La cuenta para mostrar el saldo
         Cuenta cuenta = cliente.getCuentas().get(indiceCuenta - 1);
         Pantalla pantallaVerSaldo = new Pantalla("Ver saldo");
         List<String> contenidoVerSaldo = new ArrayList();
         contenidoVerSaldo.add("Cliente: " + cliente.getNombre());
         contenidoVerSaldo.add("Nro Cuenta: " + cuenta.getNroCuenta());
         contenidoVerSaldo.add("Saldo: " + cuenta.getMoneda() + " " + cuenta.getSaldo());
         pantallaVerSaldo.setContenido(contenidoVerSaldo);
         pantallaVerSaldo.desplegar();
     }

    private void depositar() {
        Pantalla pantallaListadoCuentas = construirPantallaListadoCuentas();
        List<Object> datosIntroducidos = pantallaListadoCuentas.desplegar();
        int indiceCuenta = (Integer) datosIntroducidos.get(0);
       

        
    }

    private void retirar() {
        Pantalla pantallaListadoCuentas = construirPantallaListadoCuentas();
        List<Object> datosIntroducidos = pantallaListadoCuentas.desplegar();
        int indiceCuenta = (Integer) datosIntroducidos.get(0);

        
    }

    public Pantalla construtirPantallaMovientos(Boolean flagDeposito){
       Pantalla solicitudMonto = new Pantalla(flagDeposito?"Deposito":"Retiro");
       solicitudMonto.definirDatoEntrada("Ingrese el monoto: ", "Integer");
       return solicitudMonto;
    }

    /**
     * Construccion de pantalla para mostrar las cuentas
     * @return
     */
    public Pantalla construirPantallaListadoCuentas(){
        List<String> listadoCuentasContenido = new ArrayList();
        listadoCuentasContenido.add(" Elija una sus cuentas:");
        for ( int i = 0 ; i < cliente.getCuentas().size() ; i ++ ) {
            Cuenta cuenta = cliente.getCuentas().get(i);
            listadoCuentasContenido.add( (i + 1) + " " + cuenta.getNroCuenta() 
                    + " " + cuenta.getTipo());
        }
        Pantalla pantallaListadoCuentas = new Pantalla("Sus cuentas");
        pantallaListadoCuentas.definirDatoEntrada("Seleccione una opci�n: ", "Integer");
        pantallaListadoCuentas.setContenido(listadoCuentasContenido);
        return pantallaListadoCuentas;
        
    }
    
    
    
}
