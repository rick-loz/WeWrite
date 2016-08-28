package dankmemes.wewrite;

/*
Esta es la clase principal de la aplicación.
En esta clase se despliega la pantalla de bienvenida y se realiza el proceso de login con Facebook.
Si el usuario ya ha iniciado sesión con Facebook en la aplicación anteriormente, automáticamente lleva al usuario a la
siguiente pantalla, que consiste en la información de tarjeta de crédito y se encuentra en la clase CreditCard.java
 */

//Android
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

//Facebook
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;



public class Login extends Activity {


    private LoginButton loginButton; //Con esta variable se declarará el botón de login con fb
    private CallbackManager callbackManager; //Esta variable se encarga de manejar la información recibida por el botón de login
    private boolean isMainLobbyStarted = false; //Para saber si el usuario ya inició sesión previamente
    private AccessTokenTracker accessTokenTracker; //El acces token permite al usuario utilizar el API de Fb y obtiene información del usuario. El access token tracker monitorea las acciones del access token

    private Handler mHandler = new Handler();
    private String name;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());


        callbackManager = CallbackManager.Factory.create();

        /*Se incializa el access token tracker. El access token incia como null, entonces, cuando el usuario haga login, el access token
        cambiará y se ejecutará el método updateWithToken
         */
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken newAccessToken) {
                updateWithToken(newAccessToken);
            }
        };

        //Si ya se inició sesión anteriormente, el access token no cambiará, así que se tiene que utilizar este método para automáticamente pasar a la siguiente actividad
        updateWithToken(AccessToken.getCurrentAccessToken());


        setContentView(R.layout.activity_login); //Se declara el layout, lo que se verá en la pantalla. Estas se encuentran en la carpeta Turismo\app\src\main\res\layout

        loginButton = (LoginButton)findViewById(R.id.login_button); //Se define el botón de login en la pantalla


        loginButton.setReadPermissions("email"); //Método de fb para pedir permiso al usuario de leer cierta información además de la básica (nombre). En este caso, el email


        //Este método indica que es lo que sucede cuendo se hace login con fb
        loginButton.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {

                    //En caso de que el login sea exitoso
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        //El GraphRequest permite obtener la información del usuario y guardarle en un String
                        //Obtenido de: http://code.tutsplus.com/tutorials/quick-tip-add-facebook-login-to-your-android-app--cms-23837 en la sección de comentarios
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object,GraphResponse response) {
                                        try {
                                            name = object.getString("name");
                                            email = object.getString("email");
                                            Log.i("FB", name + email);

                                        } catch(JSONException ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                });

                        //Guarda la información en un Bundle
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();

                        //Después de 1 segundo, se inicia la siguiente actividad/clase.
                        //Es necesario esperar un segundo para obtener la información de fb, porque esto toma un poco de tiempo.
                        //Si no se espera, puede que la información no se obtenga.
                        mHandler.postDelayed(new Runnable() {
                            public void run() {
                                Intent intent = new Intent(Login.this, Categories.class); //Se indica la actividad a iniciar
                                intent.putExtra("personName", name); //Se pasa el string name a la actividad que va a ser iniciar
                                intent.putExtra("personEmail", email); //Se pasa el string email a la actividad que va a ser iniciar
                                if(!isMainLobbyStarted) { //Si no se ha iniciado sesión...
                                    startActivity(intent);//Iniciar la siguiente actividad
                                }
                            }
                        }, 1000);



                    }

                    //Si se cancela el proceso de login
                    @Override
                    public void onCancel() {
                        Log.i("FB","cancel");

                    }

                    //Si ocurre un error a la hora de login
                    @Override
                    public void onError(FacebookException e) {
                        Log.i("FB","Fail");


                    }
                });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //Este método se utiliza para pasar a la siguiente actividad automáticamente
    //Obtenido de: http://stackoverflow.com/questions/29294015/how-to-check-if-user-is-logged-in-with-fb-sdk-4-0-for-android
    private void updateWithToken(AccessToken currentAccessToken) {

        if (currentAccessToken != null) { //Básicamente, el método no sirve si no hay una access token inicializada)

            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object,GraphResponse response) {
                            try {
                                name = object.getString("name");
                                email = object.getString("email");
                                Log.i("FB", name + email);

                            } catch(JSONException ex) {
                                ex.printStackTrace();
                            }
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,email,gender, birthday");
            request.setParameters(parameters);
            request.executeAsync();

            mHandler.postDelayed(new Runnable() {
                public void run() {
                    Intent intent = new Intent(Login.this, Categories.class);
                    intent.putExtra("personName", name);
                    intent.putExtra("personEmail", email);
                    if(!isMainLobbyStarted) {
                        startActivity(intent);
                    }
                }
            }, 1000);


        }
    }


}

