<template>
  <v-app>
    <v-main>
      <Header />
      <div id="content-wrapper">
        <router-view />
      </div>
      <Footer/>
    </v-main>
  </v-app>
</template>

<script>
import Header from './components/header.vue'
import Footer from './components/footer.vue'
import router from './router/router';

export default {
  name: 'App',
  components: {
    Header,
    Footer,
    router
},
}
</script>