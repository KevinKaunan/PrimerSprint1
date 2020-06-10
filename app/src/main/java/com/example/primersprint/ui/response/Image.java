package com.example.primersprint.ui.response;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("key")
    @Expose
    private Integer key;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("nombreAlbum")
    @Expose
    private String nombreAlbum;
    @SerializedName("base64")
    @Expose
    private String base64;
    @SerializedName("localizacion")
    @Expose
    private String localizacion;
    @SerializedName("Numero")
    @Expose
    private Integer numero;

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

}
