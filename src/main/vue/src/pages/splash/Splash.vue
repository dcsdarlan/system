<template>
  <v-app id="splash-page">
    <v-content>
      <v-container fluid fill-height>
        <v-layout align-center justify-center>
          <v-flex xs12 sm8 md4>
            <v-card class="elevation-12">
              <div class="container-center">
                <img class="splash-image" :src="$getConst('IMAGE_PATH') + 'izicash.png'">
              </div>

              <v-card-text>
                <div class="container-center">
                  <v-progress-linear class="splash-progress" v-model="progress"></v-progress-linear>
                  <!--
                  <v-progress-circular class="splash-progress" indeterminate color="primary"></v-progress-circular>
                  -->
                  <div class="splash-text" color="secundary">{{text}}...</div>
                </div>
              </v-card-text>
            </v-card>
          </v-flex>
        </v-layout>
      </v-container>
    </v-content>
  </v-app>
</template>

<script>
  import axios from 'axios';
  import CONFIG from '../../config';

  export default {
  name: 'splash-page',
  created(){
    console.log("Splash");
    setTimeout(() => {
      this.progress = 33.3;
      this.text = "Carregando bibliotecas...";
    }, 1000);
    setTimeout(() => {
      this.progress = 66.6;
      this.text = "Carregando imagens...";
    }, 2000);
    setTimeout(() => {
      this.progress = 100;
    } , 3000);
    setTimeout(() => {
      this.token = sessionStorage.getItem("authorization");
      var headers = {'authorization': "Bearer " + this.token};
      axios.get(CONFIG.URL_API + 'auth/user',{ headers: headers })
        .then(response => {
          console.log("----- Usuário -----");
          console.log(response.data);
          console.log("-------------------");
          this.$router.replace({ name: 'home' });
        })
        .catch(e => {
          this.$router.replace({ name: 'auth' });
        });
    } , 3500);
  },
  mounted() {
    this.text = "Iniciando...";
  },
  data: () => ({
    drawer: null,
    text: "",
    progress: 0,
    locale: ""
  })
}
</script>

<style lang="scss">
  @import "../../assets/styles/theme.scss";

  .splash-page {
    background-color: #3f8ab7;
  }
  .splash-image {
    margin-top: 20px;
    height: 50px;
  }
  .splash-progress {
    margin-bottom: 10px;
  }
  .container-center {
    display: block;
    width: 100%;
    text-align: center;
  }
</style>
