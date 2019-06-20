package com.example.agustin.festnowapp.Util;

/**
 * Clase que procesa según una provincia obtenido su comunidad autonoma
 */
public class UtilGeo {

    public static String procesarComunidad (String provincia){
        String comunidad = "";


            switch (provincia){
                case "Badajoz":
                    comunidad = "Extremadura";
                    break;
                case "Cáceres":
                    comunidad = "Extremadura";
                    break;
                case "Huelva":
                    comunidad = "Andalucía";
                    break;
                case "Sevilla":
                    comunidad = "Andalucía";
                    break;
                case "Almería":
                    comunidad = "Andalucía";
                    break;
                case "Cádiz":
                    comunidad = "Andalucía";
                    break;
                case "Córdoba":
                    comunidad = "Andalucía";
                    break;
                case "Granada":
                    comunidad = "Andalucía";
                    break;
                case "Jaén":
                    comunidad = "Andalucía";
                    break;
                case "Málaga":
                    comunidad = "Andalucía";
                    break;
                case "Huesca":
                    comunidad = "Aragón";
                    break;
                case "Teruel":
                    comunidad = "Aragón";
                    break;
                case "Zaragoza":
                    comunidad = "Aragón";
                    break;
                case "Asturias":
                    comunidad = "Principado de Asturias";
                    break;
                case "Menorca":
                    comunidad = "Islas Baleares";
                    break;
                case  "Mayorca":
                    comunidad = "Islas Baleares";
                    break;
                case "Ibiza":
                    comunidad = "Islas Baleares";
                    break;
                case "A Coruña":
                    comunidad = "Galicia";
                    break;
                case "Lugo":
                    comunidad = "Galicia";
                    break;
                case "Pontevedra":
                    comunidad = "Galicia";
                    break;
                case "Orense":
                    comunidad = "Galicia";
                    break;
                case  "Cantabria":
                    comunidad = "Cantabria";
                    break;
                case "Vizcaya":
                    comunidad = "Pais Vasco";
                    break;
                case "Álava":
                    comunidad = "Pais Vasco";
                    break;
                case "Guipúzcua":
                    comunidad = "Pais Vasco";
                    break;
                case "Navarra":
                    comunidad = "Navarra";
                    break;
                case  "La Rioja":
                    comunidad = "La Rioja";
                    break;
                case "Lleida":
                    comunidad = "Cataluña";
                    break;
                case "Girona":
                    comunidad = "Cataluña";
                    break;
                case "Barcelona":
                    comunidad = "Cataluña";
                    break;
                case "Tarragona":
                    comunidad = "Cataluña";
                    break;
                case "Castellón":
                    comunidad = "Valencia";
                    break;
                case "Valencia":
                    comunidad = "Valencia";
                    break;
                case "Alicante":
                    comunidad = "Valencia";
                    break;
                case  "Murcia":
                    comunidad = "Murcia";
                    break;
                case  "La Palma":
                    comunidad = "Islas Canarias";
                    break;
                case "Tenerife":
                    comunidad = "Islas Canarias";
                    break;
                case "La Gomera":
                    comunidad = "Islas Canarias";
                    break;
                case "El Hierro":
                    comunidad = "Islas Canarias";
                    break;
                case  "Gran Canaria":
                    comunidad = "Islas Canarias";
                    break;
                case "Fuerteventura":
                    comunidad = "Islas Canarias";
                    break;
                case "Lanzarote":
                    comunidad = "Islas Canarias";
                    break;
                case  "Ceuta":
                    comunidad = "Ceuta";
                    break;
                case "Melilla":
                    comunidad = "Melilla";
                    break;
                case "Madrid":
                    comunidad = "Madrid";
                    break;
                case "Albacete":
                    comunidad = "Castilla la Mancha";
                    break;
                case  "Ciudad Real":
                    comunidad = "Castilla la Mancha";
                    break;
                case  "Toledo":
                    comunidad = "Castilla la Mancha";
                    break;
                case  "Cuenca":
                    comunidad = "Castilla la Mancha";
                    break;
                case "Guadalajara":
                    comunidad = "Castilla la Mancha";
                    break;
                case "Ávila":
                    comunidad = "Castilla y León";
                    break;
                case  "Salamanca":
                    comunidad = "Castilla y León";
                    break;
                case  "Segovia":
                    comunidad = "Castilla y León";
                    break;
                case "Valladolid":
                    comunidad = "Castilla y León";
                    break;
                case  "Zamora":
                    comunidad = "Castilla y León";
                    break;
                case  "León":
                    comunidad = "Castilla y León";
                    break;
                case "Palencia":
                    comunidad = "Castilla y León";
                    break;
                case "Burgos":
                    comunidad = "Castilla y León";
                    break;
                case "Soria":
                    comunidad = "Castilla y León";
                    break;
                    default:
                        comunidad = "default";
                        break;
            }




        return comunidad;
    }
}


