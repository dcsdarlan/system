<template>
  <v-app id="companies-update-page">
    <v-container class="container-main" fluid>
      <v-layout align-top justify-center>
        <v-flex xs12 sm12 md12>
          <v-card class="elevation-2">
            <v-toolbar color="white" class="elevation-1 container-center">
              <div>
                <v-toolbar-title>Alterar Empresa</v-toolbar-title>
              </div>
            </v-toolbar>
            <v-alert v-model="alert" dismissible type="success">
              Item salvo com sucesso
            </v-alert>
            <v-card-text>
              <v-form>
                <v-text-field :readonly="saved" name="company" label="nome" v-model="company.name" type="text"></v-text-field>
              </v-form>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn @click="back" right color="error">Voltar</v-btn>
              <v-btn @click="save" v-if="!saved" right color="info">Salvar</v-btn>
            </v-card-actions>
          </v-card>
        </v-flex>
      </v-layout>
      <v-dialog v-model="dialog" persistent width="200" height="80">
        <v-card>
          <v-card-text>
            <div class="header-dialog">Processando...</div>
            <div class="container-center">
              <v-progress-circular class="splash-progress" indeterminate color="primary"></v-progress-circular>
            </div>
            <!-- <v-progress-linear indeterminate color="primary" class="mb-0" ></v-progress-linear> -->
          </v-card-text>
        </v-card>
      </v-dialog>
      <v-dialog v-model="load" persistent width="200" height="80">
        <v-card>
          <v-card-text>
            <div class="header-dialog">Carregando...</div>
            <div class="container-center">
              <v-progress-circular class="splash-progress" indeterminate color="primary"></v-progress-circular>
            </div>
          </v-card-text>
        </v-card>
      </v-dialog>
      <v-snackbar v-model="snackbar" :bottom="true" :color="color" :timeout="3000">
        {{ error }}
      </v-snackbar>
    </v-container>
  </v-app>
</template>

<script>
  import axios from 'axios';
  import CONFIG from '../../config';

  export default {
    name: 'companies-update-page',
    methods: {
      validate: function() {
        if(!this.company.name) {
          this.error = "Você precisa definir um nome válido";
          return false;
        }
        return true;
      },
      back: function (event) {
        this.$router.push({ name: 'companies' });
      },
      save: function (event) {
        console.log(this.user);
        this.dialog = true;
        this.snackbar = !this.validate();
          let companyData = {
              name: this.company.name
          };

        this.token = sessionStorage.getItem("authorization");
        let headers = {'Authorization': "Bearer " + this.token};

        if(!this.snackbar){
          axios.put(CONFIG.URL_API + 'companies/' + this.id, companyData, { headers: headers })
            .then(response => {
              this.dialog = false;
              if(response.status == 200) {
                console.log(response);
                this.company = response.data;
                this.alert = true;
                this.saved = true;
              }
            })
            .catch(e => {
              console.log(e);
              this.dialog = false;
                if(e.response && e.response.status == 401) {
                    this.error = "Credenciais inválidas";
                    this.snackbar = true;
                }
                if(e.response && e.response.status == 500) {
                    this.error = "Ocorreu um erro no servidor";
                    this.snackbar = true;
                }
                if(!e.response) {
                    this.error = "Impossível acessar o servidor";
                    this.snackbar = true;
                }
            });
        } else {
          this.dialog = false;
        }
      }
    },
    mounted() {

    },
    created() {
      this.load = true;
      console.log(this.$route.params.id);
      this.id = this.$route.params.id;
      this.token = sessionStorage.getItem("authorization");
      var headers = {Authorization: "Bearer " + this.token};
      axios.get(CONFIG.URL_API + 'companies/' + this.id, { headers: headers }).then(response => {
        console.log(response);
        this.company = response.data;
        this.load = false;
      }).catch(e => {
        console.log(e);
        this.load = false;
        this.error = "Erro ao tentar carregar o item";
        this.snackbar = true;
      });
    },
    data: () => ({
      id: 0,
      company: {
          name: "",
      },
      load: false,
      snackbar: false,
      error: "",
      dialog: false,
      alert: false,
      saved: false,
      color: "error"
    })
  }
</script>

<style lang="scss">
  .container-main {
    background-color: #ccc;

  }
  .footer-text {
    color: #ffffff;
    margin-right: 10px;
  }
  .login-image {
    margin: 10%;
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
  .container-center {
    display: block;
    width: 100%;
    text-align: center;
  }
  .actions-header {
    text-align: right;
  }
  .splash-progress {
    margin-top: 10px;
    margin-bottom: 0px;
  }
</style>
