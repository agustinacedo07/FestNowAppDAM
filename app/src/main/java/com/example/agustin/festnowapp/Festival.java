package com.example.agustin.festnowapp;

public class Festival {

        private String nombre;
        private char genero;
        public Festival(String nombre, char genero) {
            this.nombre=nombre;
            this.genero=genero;
        }
        public String getNombre() {
            return nombre;
        }
        public char getGenero() {
            return genero;
        }

}
