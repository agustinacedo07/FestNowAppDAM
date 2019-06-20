package com.example.agustin.festnowapp.Adaptadores;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.agustin.festnowapp.Activitys.PantallaDetalleFestival;
import com.example.agustin.festnowapp.Fragments.FragmentArtistas;
import com.example.agustin.festnowapp.Fragments.FragmentComentarios;
import com.example.agustin.festnowapp.Fragments.FragmentInformacion;
import com.example.agustin.festnowapp.Fragments.FragmentNoticias;
import com.example.agustin.festnowapp.Fragments.FragmentSeguidores;
import com.example.agustin.festnowapp.Fragments.FragmentValoraciones;

import modelos.Festival;

/**
 * @author Agustin/Adrian
 * Clase que crear un conjunto de fragment dinámicos para mostrar la información al detalle de cada festival
 */
public class AdminFragmentAdapter extends FragmentPagerAdapter {
    private Festival festival;
    private Context contexto;

    //constructor
    public AdminFragmentAdapter(FragmentManager fm, Festival festival, Context contextoPadre) {
        super(fm);
        this.festival = festival;
        this.contexto = contextoPadre;
    }

    /**
     * Evalua la posición del fragment y crea y lanza el Fragment creado
     * @param posicion - posición del fragment
     * @return - la vista del fragment que se ha de mostrar
     */
    @Override
    public Fragment getItem(int posicion) {
        try{
            switch (posicion){
                case 0://informacion del festival
                    return FragmentInformacion.newInstance(festival);
                case 1://artistas del festival
                    return FragmentArtistas.newInstance(festival,contexto);
                case 2://noticias del festival
                    return FragmentNoticias.newInstance(festival,contexto);
                case 3://seguidores
                    return  FragmentSeguidores.newInstance(festival,contexto);
                case 4://comentarios
                    return FragmentComentarios.newInstance(festival,contexto);
                case 5:
                    return FragmentValoraciones.newInstance(festival,contexto);
            }
            return  null;
        }catch(Exception e){
            return null;
        }

    }

    /**
     * Devuelve la cantidad de fragments que se van a mostrar
     * @return - cantidad de pantallas que se quierne mostrar
     */
    @Override
    public int getCount() {
        //puesto que el número es variable, según el usuario haya realizado ya o no la valoración,
        //el número de pantallas podrá cambiar
        return PantallaDetalleFestival.numPantallas;
    }

    /**
     * Establece el título de la pantalla que se está mostrando
     * @param position
     * @return
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0://informacion del festival
                return "Información";
            case 1://artistas del festival
                return "Artistas";
            case 2://noticias del festival
                return "Noticias";
            case 3://seguidores
                return "Lista de Seguidores";
            case 4://comentarios
                return "Comentarios";
            case 5://valoracion del festival
                return "Valora el Festival";
        }

        return null;
    }
}
