import './assets/css/base.css'
import './assets/css/header.css'
import './assets/css/footer.css'
import './assets/css/error.css'

import { createApp } from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify'
import { loadFonts } from './plugins/webfontloader'
import router from './router/router'



loadFonts()

createApp(App)
  .use(vuetify)
  .use(router)
  .mount('#app')
