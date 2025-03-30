package com.example.auth2grupo3.RetroFit;

import com.example.auth2grupo3.modelo.ProductoModel;
import com.example.auth2grupo3.modelo.UsuarioModel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiService {
    //Autentificacion
    @FormUrlEncoded
    @POST("auth/")
    Call<UsuarioModel> login(
            @Field("grant_type") String grantType,
            @Field("username") String userName,
            @Field("password") String password,
            @Field("client_id") String clientId,
            @Field("client_secret") String clientSecret
    );

    @POST("user/")
    Call<String> registrarUsuario(@Body Map<String, String> body);

    @POST("producto/")
    Call<String> registrarProducto(
            @Header("Authorization") String token,
            @Body Map<String, String> body
    );

    @GET("producto/")
    Call<List<ProductoModel>> obtenerProductos(@Header("Authorization") String token);

    @GET("producto/{id}")
    Call<ProductoModel> obtenerProductoPorId(@Path("id") int id);

    @GET("producto/nombre/{nombre}")
    Call<List<ProductoModel>> obtenerProductosPorNombre(@Path("nombre") String nombre);

    @PUT("producto/{id}")
    Call<String> actualizarProducto(
            @Header("Authorization") String token,
            @Path("id") int id,
            @Body HashMap<String,String> body
    );

    @DELETE("producto/{id}")
    Call<String> eliminarProducto(@Header("Authorization") String token,@Path("id") int id);
}
