<template>
  <v-app id="companies-page">
    <v-container class="container-main" fluid>
      <v-dialog v-model="dialogRemove" max-width="400">
        <v-card>
          <v-card-title class="headline">Atenção</v-card-title>
          <v-card-text>
            Tem certeza que deseja excluir esse registro?
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn  color="darken-1" flat="flat" @click="dialogRemove = false">Cancelar</v-btn>
            <v-btn color="error darken-1" flat="flat" @click="remove">Confirmar</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
      <v-dialog v-model="load" persistent width="200" height="80">
        <v-card>
          <v-card-text>
            <div class="header-dialog">Processando...</div>
            <div class="container-center">
              <v-progress-circular class="splash-progress" indeterminate color="primary"></v-progress-circular>
            </div>
          </v-card-text>
        </v-card>
      </v-dialog>
      <v-layout align-top justify-center>
        <v-flex xs12 sm12 md12>
          <v-toolbar class="elevation-1" flat color="white">
            <v-toolbar-title>Empresas</v-toolbar-title>
            <v-spacer></v-spacer>
            <v-btn color="info" class="white--text" @click="create">
              <v-icon dark>add</v-icon>Adicionar
            </v-btn>
          </v-toolbar>
          <v-data-table :headers="[
          {text: 'Id', value: 'id'},
            {text: 'Nome', value: 'name'},
            { text: '', value: 'name', sortable: false }
          ]" :items="itens" :loading="loading" rows-per-page-text="Linhas" :rows-per-page-items="[100, 200, { text: 'Todos', value: -1 }]" no-results-text="Não há dados" no-data-text="Não há dados" :pagination.sync="pagination" class="elevation-1">
            <v-progress-linear slot="progress" color="primary" indeterminate></v-progress-linear>
            <template slot="items" slot-scope="props">
              <td>{{ props.item.id }}</td>
              <td>{{ props.item.name }}</td>
              <td class="justify-center px-0">
                <v-tooltip top>
                  <v-btn slot="activator" @click="editItem(props.item)" fab small color="warning">
                    <v-icon>
                      edit
                    </v-icon>
                  </v-btn>
                  <span>Editar</span>
                </v-tooltip>
                <v-tooltip top>
                  <v-btn slot="activator" @click="removeItem(props.item)" fab small color="error">
                    <v-icon>
                      delete
                    </v-icon>
                  </v-btn>
                  <span>Excluir</span>
                </v-tooltip>
              </td>
            </template>
            <template slot="pageText" slot-scope="item">
              {{pagination.pageStart}} - {{pagination.pageStop}} de {{pagination.totalItems}}
            </template>
          </v-data-table>
          <div class="text-xs-center pt-2">
            <v-pagination circle @input="onPageChange" :total-visible="7" v-model="page" :length="pages"></v-pagination>
          </div>
        </v-flex>
      </v-layout>
    </v-container>
  </v-app>
</template>

<script>
  import axios from 'axios';
  import CONFIG from '../../config';

  export default {
    name: 'companies-page',
    methods: {
      create: function (event) {
        console.log("criar usuário...");
        this.$router.push({ name: 'companies.create' });
      },
      editItem(item) {
        this.$router.push({ name: 'companies.edit', params: {id: item.id }});
      },
      removeItem(it) {
        this.itemSelected = JSON.parse(JSON.stringify(it));
        this.dialogRemove = true;
      },
      remove: function (event) {
        this.dialogRemove = false;
        this.load = true;
        this.token = sessionStorage.getItem("authorization");
        var headers = {Authorization: "Bearer " + this.token};
        axios.delete(CONFIG.URL_API + 'companies/' + this.itemSelected.id, { headers: headers }).then(response => {
          console.log(response);
          for(var i in this.itens){
            if(this.itens[i].id == this.itemSelected.id){
              this.itens.splice(i, 1);
            }
          }
          this.load = false;
        }).catch(e => {
          console.log(e);
          this.load = false;
          this.error = "Ocorreu um erro ao listar as empresas";
          this.snackbar = true;
        });
      },
      list() {
        this.token = sessionStorage.getItem("authorization");
        var headers = {Authorization: "Bearer " + this.token};
        axios.get(CONFIG.URL_API + 'companies/' + this.pagination.rowsPerPage + "/" + (this.page - 1), { headers: headers }).then(response => {
          console.log(response);
          this.itens = response.data.content;
          this.pagination.totalItems = response.data.totalElements;
          this.pagination.pageStart = (this.pagination.rowsPerPage * (this.page - 1)) + 1;
          this.pagination.pageStop = this.pagination.pageStart + response.data.numberOfElements - 1;
          if(response && response.status == 204) {
            this.itens = []
          }
          this.loading = false;
        }).catch(e => {
          console.log(e);
          this.load = false;
            this.error = "Ocorreu um erro ao listar as empresas";
          this.snackbar = true;
          console.log(e);
        });
      },
      onPageChange(newPage) {
        this.token = sessionStorage.getItem("authorization");
        //this.pagination.page = newPage;
        let headers = {'Authorization': "Bearer " + this.token};
        this.page = newPage;
        console.log("Interação: " + this.page);
        axios.get(CONFIG.URL_API + 'companies/' + this.pagination.rowsPerPage + "/" + (this.page - 1), { headers: headers }).then(response => {
          console.log(response);
          this.itens = response.data.content;
          this.pagination.totalItems = response.data.totalElements;
          this.pagination.pageStart = (this.pagination.rowsPerPage * (this.page - 1)) + 1;
          this.pagination.pageStop = this.pagination.pageStart + response.data.numberOfElements - 1;
          if(response && response.status == 204) {
            this.itens = []
          }
          this.loading = false;
        }).catch(e => {
          console.log(e);
          this.load = false;
            this.error = "Ocorreu um erro ao listar as empresas";
          this.snackbar = true;
        });
      }
    },
    mounted() {
      /*
      this.connect();
      for (let i = 0; i < this.items.length; i += 1) {
        const item = this.desserts[i];
        this.$set(this.$refs.dTable.expanded, item.name, true);
      }*/
    },
    computed: {
      pages () {
        if (this.pagination.rowsPerPage == null ||
          this.pagination.totalItems == null
        ) return 0;
        return Math.ceil(this.pagination.totalItems / this.pagination.rowsPerPage);
      }
    },
    created() {
      this.list();
    },
    data: () => ({
      pagination: {},
      search: '',
      dialogRemove: false,
      loading: true,
      itens: [
      ],
      load: false,
      pagination : {
        sortBy: 'id',
        descending: true,
        rowsPerPage: 100,
        totalItems: 0,
        visible: 7,
        pageStart: 0,
        pageStop: 0
      },
      page: 1,
      color: "error"
    })

  }
</script>

<style lang="scss">
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
  .container-main {
    background-color: #ccc;

  }
</style>
