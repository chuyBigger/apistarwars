package com.aluracrsos.java.principal;

import com.aluracrsos.java.modelos.Peliculas;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import java.util.Scanner;

public class Principal {
    public static void main(String[] args) throws IOException, InterruptedException {

        Scanner lectura = new Scanner(System.in);

        // mapeo de peliculas conversion de fecha de lanzamiento a cronologia de la saga
        int[] mapeoOrdenDeLAnzamiento = {4,5,6,1,2,3};


        // Configuramos GSON para formatear las respuestas de manera más legible
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE) // Convierte nombres de atributos a UpperCamelCase (lo que espera la API)
                .setPrettyPrinting()  // Hace que el JSON resultante sea más legible
                .create();

        // todo Array para guardar la lista de busquedas aqui hay un error
        ArrayList<Peliculas> peliculasConsultadas = new ArrayList<>();

        // Saludo principal APP
        System.out.println("App Consulta de detalles le las peliculas se Star Wars 1 a la 9 ");
        System.out.println("================================================================\n");

        // Ciclo principal de la aplicación que se repite hasta que el usuario ingresa 0
        while (true) {

            System.out.println("""
                    ====================================================================
                    Que episodio (Pelicula) deseas cunsultar 
                    el catalogo solo cuenta con las primeras 6 peliculas
                    ingresa el numero de episodio para seleccionar uno: " 1 al 6 "
                    o ingresa la " 0 " para terminar
                    ********************************************************************
                    """);

            // Lee el número del episodio seleccionado por el usuario
            int seleccion = lectura.nextInt();

            // Mapeamos el episodio cronológico al número de lanzamiento (ID de API)
            int idApi = mapeoOrdenDeLAnzamiento[seleccion-1];

            // Crea la URL con el número del episodio
            String direccion = "https://swapi.py4e.com/api/films/" + idApi + "/";

            // Si el usuario ingresa 0, termina el programa
            if (seleccion == 0) {
                System.out.println("Haz Salido la 'APP'");
                break;
            } else if (seleccion <= 0 || seleccion >=6) {
                System.out.println("El valor ingresado no es correcto");
                System.out.println("vuelve a intentarlo");
                continue;
            }

            // Configuramos el cliente HTTP para enviar una solicitud
            HttpClient client = HttpClient.newHttpClient();
            // Preparamos la solicitud HTTP con la URL correspondiente al episodio
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(direccion)) // Especifica la dirección del endpoint
                    .build();

            try {
                // Enviamos la solicitud y obtenemos la respuesta en formato String
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                // Verificar si la respuesta es exitosa
                if (response.statusCode() == 200) {

                    // Cuerpo de la respuesta, que es un JSON
                    String json = response.body();

                    // Convierte el Json toString para definirlo a travez de la clase Peliculas
                    Peliculas peliculas = gson.fromJson(json, Peliculas.class);

                    // Imprime Peliculas corregido
                    System.out.println(peliculas);

                    // Agrega el registro de busqueda al array
                    peliculasConsultadas.add(peliculas);

                    // Convertir el JSON a un objeto y luego imprimirlo de manera legible
                    //JsonElement jsonElement = gson.fromJson(json, JsonElement.class);

                    // Imprimir el objeto de manera bonita
                    //System.out.println(gson.toJson(jsonElement));
                }else {
                    // Regresa en la consola un error
                    System.out.println("No se pudo obtener información para el episodio " + seleccion);
                }
            }catch (IOException | InterruptedException e){
                System.out.println("Eror al realizar la solicitud");
                e.printStackTrace();
            }


        }
        if (!peliculasConsultadas.isEmpty()){
            FileWriter impresionDeRegistros = new FileWriter("peliculas_registro_consultas.json");
            gson.toJson(peliculasConsultadas, impresionDeRegistros);
            impresionDeRegistros.close();
            System.out.println("registro de peliculas guardado en 'peliculas_registro_consultas.json'");
        }


    }
}
