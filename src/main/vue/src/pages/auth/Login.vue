<template>
  <v-app id="login-page">
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-form @submit.prevent="logon">
              <v-card class="elevation-12">
                  <v-layout row wrap>
                    <v-flex class="container-image" xs4>
                      <v-container fill-height class="elevation-0">
                        <div class="container-center">
                          <div class="auth-image-contaner">
                            <img class="auth-image" :src="$getConst('IMAGE_PATH') + 'izicash-favicon.png'">
                          </div>
                          <br>
                          <div class="welcome">Bem vindo</div>
                          <br>
                          <div class="welcome version"><a class="link-changelog" href="javascript:void(0)" @click.prevent="changelog">{{sysVersion}}</a></div>
                          <div class="welcome version">{{sysBuild}}</div>
                          <div class="welcome version">Copyright © 2019 Izicash</div>
                        </div>
                      </v-container>
                    </v-flex>
                    <v-flex xs8>
                      <v-toolbar color="white" class="elevation-0 container-center">
                          <v-toolbar-title class="auth-title">{{$getConst('NAME')}}</v-toolbar-title>
                          <v-spacer></v-spacer>
                      </v-toolbar>
                      <v-card-text>
                      <v-text-field :disabled="load" append-icon="person" name="user" label="Login" v-model="login.login" type="text"></v-text-field>
                      <v-text-field :disabled="load" append-icon="lock" name="password" label="Senha" v-model="login.password" type="password" id="password"></v-text-field>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn type="submit" color="primary" :loading="load" :disabled="load">Entrar</v-btn>
                      </v-card-actions>
                      </v-card-text>
                    </v-flex>
                  </v-layout>
              </v-card>
            </v-form>
          </v-flex>
        </v-layout>
        <v-snackbar v-model="snackbar" :bottom="true" :color="color" :timeout="3000">
          {{ error }}
        </v-snackbar>
      </v-container>
    </v-content>
  </v-app>
</template>
<script>
  import axios from 'axios';
  import CONFIG from '../../config';

  export default {
    name: 'login-page',
    methods: {
      validate: function() {
        if(!this.login.login) {
          this.error = "Você precisa especificar um login válido";
          return false;
        }
        if(!this.login.password) {
          this.error = i18n.t("Você precisa especificar uma senha válida");
          return false;
        }
        return true;
      },
      logon: function (event) {
        this.load = true;
        console.log(this.login);
        this.snackbar = !this.validate();
        let loginData = {
          login: this.login.login,
          password: this.login.password
        };
        if(!this.snackbar){
          axios.defaults.headers.common['Access-Control-Expose-Headers'] = 'Authorization';
          axios.post(CONFIG.URL_API + 'auth/logon', loginData)
            .then(response => {
              this.load = false;
              if(response && response.status == 200) {
                console.log(response);
                var token = response.headers.authorization.split(" ");
                sessionStorage.setItem("authorization", token[1]);
                this.$router.push({ name: 'home' });
              }
              if(!response) {
                this.error = "Servidor indisponível";
                this.snackbar = true;
              }
            })
            .catch(e => {
              console.log(e);
              this.load = false;
              if(e.response && e.response.status == 401) {
                this.error = "Credenciais inválidas";
                this.snackbar = true;
              }
              if(e.response && e.response.status == 500) {
                this.error = "Ocorreu um erro no servidor";
                this.snackbar = true;
              }
              if(!e.response) {
                this.error = this.error = "Servidor indisponível";
                this.snackbar = true;
              }
            })
        } else {
          this.load = false;
        }
      },
      changeLocale(locale) {
        i18n.locale = locale;
        localStorage.setItem("locale", i18n.locale);
        this.locale = i18n.locale;
      }
    },
    mounted() {
      axios.get(CONFIG.URL_API)
        .then(response => {
          // var tk = JSON.parse();
          console.log("----- Api -----");
          console.log(response.data);
          console.log("---------------");
          var system = response.data;
          this.sysVersion = system.version;
          this.sysBuild = system.build;
        })
        .catch(e => {
          console.log("----- Erro -----");
          console.log(e);
          console.log("----------------");
        });
    },
    data: () => ({
      login: {
        login: "",
        password: ""
      },
      sysVersion: "",
        sysBuild: "",
      snackbar: false,
      error: "",
      load: false,
      drawer: null,
        color: "",
    })
  }
</script>

<style lang="scss">
  @import "../../assets/styles/theme.scss";

  .auth-title {
    color: #3f8ab7;
  }
  .container-image {
    background-color: #3f8ab7 !important;
  }
  .v-menu--inline {
    width: 70px;
  }
  .welcome {
    font-size: 15px;
    color: #ffffff;
  }
  .auth-image-contaner {
    margin: 0 auto;
    padding: 20px;
    background: #ffffff;
    border-radius: 50%;
    height: 130px;
    width: 130px;
  }
  .auth-image {
    margin-top: 20%;
    height: 60px;
  }
  .header-dialog {
    text-align: center;
  }
  .container-center {
    display: block;
    width: 100%;
    text-align: center;
  }
  .app-title {
    margin-top: 20px;
    margin-bottom: 10px;
  }
  .splash-progress {
    margin-top: 10px;
    margin-bottom: 0px;
  }
  .version {
    font-size: 10px;
  }
  .version a {
    color: #ffffff;
  }
  .footer-text {
    color: #ffffff;
    margin-right: 10px;
    margin-left: 10px;
  }
</style>
