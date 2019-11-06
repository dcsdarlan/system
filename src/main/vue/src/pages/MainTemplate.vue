<template>
  <v-app id="main-template">
    <v-navigation-drawer left v-model="drawerLeft" fixed app>
      <v-list dense>
        <v-toolbar flat class="transparent">
          <v-list class="pa-0">
            <v-list-tile>
              <v-list-tile-avatar :tile="true">
                <img class="image-menu" :src="$getConst('IMAGE_PATH') + 'izicash-favicon.png'">
              </v-list-tile-avatar>
              <v-list-tile-content>
                <v-list-tile-title>{{userToken}}</v-list-tile-title>
                <div class="user-category">{{userEmailToken}}</div>
              </v-list-tile-content>
            </v-list-tile>
          </v-list>
        </v-toolbar>
        <v-divider></v-divider>
        <v-list-tile @click="goMenu('home')">
          <v-list-tile-action>
            <v-icon>dashboard</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>Principal</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
        <v-list-group value="true">
          <v-list-tile slot="activator">
            <v-list-tile-title>Administração</v-list-tile-title>
          </v-list-tile>
          <v-list-tile @click="goMenu('companies')">
            <v-list-tile-action>
              <v-icon>work</v-icon>
            </v-list-tile-action>
            <v-list-tile-content>
              <v-list-tile-title>Empresas</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
          <v-list-tile @click="goMenu('users')">
            <v-list-tile-action>
              <v-icon>group</v-icon>
            </v-list-tile-action>
            <v-list-tile-content>
              <v-list-tile-title>Usuários</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
          <v-list-tile @click="exitApp">
            <v-list-tile-action>
              <v-icon>power_settings_new</v-icon>
            </v-list-tile-action>
            <v-list-tile-content>
              <v-list-tile-title>Sair</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
        </v-list-group>
        <v-dialog v-model="dialogSave" persistent width="200" height="80">
          <v-card>
            <v-card-text>
              <div class="header-dialog">Salvando...</div>
              <div class="container-center">
                <v-progress-circular class="splash-progress" indeterminate color="primary"></v-progress-circular>
              </div>
              <!-- <v-progress-linear indeterminate color="primary" class="mb-0" ></v-progress-linear> -->
            </v-card-text>
          </v-card>
        </v-dialog>
        <v-dialog v-model="dialogExit" max-width="400">
          <v-card>
            <v-card-title class="headline">Atenção</v-card-title>
            <v-card-text>
              Deseja realmente sair do sistema?
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn  color="darken-1" flat="flat" @click="dialogExit = false">Cancelas</v-btn>
              <v-btn color="error darken-1" flat="flat" @click="exit">Confirmar</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-list>
    </v-navigation-drawer>
    <!--clipped -->
    <v-toolbar color="primary" dark fixed app>
      <v-toolbar-side-icon @click.stop="togleMenu()"></v-toolbar-side-icon>
      <v-toolbar-title>{{$getConst('NAME')}}</v-toolbar-title>
      <v-spacer></v-spacer>
    </v-toolbar>
    <v-content class="container-main">
      <!--
      <v-tabs right>
        <v-tab v-for="n in 3" :key="n">
         <v-btn @click="close()" flat icon color="pink">
          <v-icon>close</v-icon>
        </v-btn>&nbsp;&nbsp;tem {{ n }}
        </v-tab>
      </v-tabs>
      -->
      <router-view></router-view>
    </v-content>

    <v-footer color="primary" app>
      <v-spacer></v-spacer>
      <span class="footer-text">Copyright © 2019 Izicash - Versão {{sysVersion}}</span>
    </v-footer>
  </v-app>
</template>

<script>
  import axios from 'axios';
  import CONFIG from '../config';

  export default {
    name: 'main-template',
    methods: {
      togleMenu() {
        this.drawerLeft = !this.drawerLeft;
        sessionStorage.setItem("drawerLeft", this.drawerLeft);
      },
      goMenu(pathUrl) {
        console.log(pathUrl);
        this.$router.push({name: pathUrl});
      },
      exitApp() {
        this.dialogExit = true;
      },
      exit() {
        sessionStorage.removeItem("authorization");
        sessionStorage.clear();
        this.$router.push({ name: 'auth'});
      },
      close() {
        console.log("fechando...");
      }
    },
    created() {
      /*
      this.drawerRight = sessionStorage.getItem("drawerRight");
      this.drawerLeft = sessionStorage.getItem("drawerLeft");
      */
    },
    mounted() {
      axios.get(CONFIG.URL_API)
        .then(response => {
          // var tk = JSON.parse();
          console.log("----- System -----");
          console.log(response.data);
          console.log("------------------");
          var system = response.data;
          this.sysVersion = system.version;
          this.sysBuild = system.sysBuild;
        })
        .catch(e => {
          console.log("----- Erro -----");
          console.log(e);
          console.log("----------------");

        });
      this.token = sessionStorage.getItem("authorization");
      var headers = {'authorization': "Bearer " + this.token};
      axios.get(CONFIG.URL_API + 'auth/user',{ headers: headers })
        .then(response => {
         // var tk = JSON.parse();
          console.log("----- Usuário -----");
          console.log(response.data);
          console.log("-------------------");
          var token = response.data;
          this.userToken = token.user.name;
          this.userEmailToken = token.user.email;
        })
        .catch(e => {
          if(e.response.status == 401) {
            this.errorProfile = "Credenciais inválidas";
            this.snackbarProfile = true;
            this.exit();
          }
          if(e.response.status == 500) {
            this.errorProfile = "Ocorreu um erro no servidor";
            this.snackbarProfile = true;
          }
          console.log("----- Erro -----");
          console.log(e);
          console.log("----------------");
          this.exit();
        });
    },
    data: () => ({
      drawerLeft: true,
      drawerRight: false,
      showBadge: false,
      dialogExit: false,
      tabs : [],
      tabItems: [],
      items: [],
      count: 0,
      userToken: "",
      userCategoryToken: "",
      userEmailToken: "",
      userCredentialToken: "",
      userPassword: "",
      dateToken: "",
      sysVersion: "",
      sysDate: "",
      dialogProfile: false,
      dialogSave: false,
      errorProfile: "",
      snackbarProfile: false,
      locale: ""
    })
  }
</script>

<style lang="scss">
  .v-menu--inline {
    width: 70px;
  }
  .link-changelog {
    color: #ffffff;
  }
  .bandeira-ativa-main {
    padding: 2px;
    border: 1px solid #ffffff;
    background: #ffffff;
    font-size: 15px;
    margin: 2px;
    cursor: pointer;
    height: 30px;
  }
  .bandeira-inativa-main {
    /* border: 1px solid #ffffff; */
    font-size: 15px;
    margin: 2px;
    cursor: pointer;
    height: 30px;
  }
  .image-ticket {
    width: 70%;
  }
  .user-category {
    font-size: 10px;
    font-weight: normal;
  }
  .footer-text {
    color: #ffffff;
    margin-right: 10px;
    margin-left: 10px;
  }
  .login-image {
    margin: 10%;
  }
  .container-main {
    background-color: #ccc;

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
</style>
