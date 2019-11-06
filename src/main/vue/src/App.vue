<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'app',
  created(){
    console.log("App");
  },
  data () {
    return {

    }
  }
}
</script>

<style lang="scss" scoped>
  @import "assets/styles/theme.scss";

  .pagina-enter-active, .pagina-leave-active {
    transition: opacity .3s
  }
  .pagina-enter, .pagina-leave-active {
    opacity: 0
  }
</style>
