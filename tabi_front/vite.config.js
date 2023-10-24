import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import vuetify from 'vite-plugin-vuetify'

// https://vitejs.dev/config/
export default defineConfig({

  plugins: [
    vue(),
    vuetify(),
    vueJsx(),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
  build: {
    outDir: "../tabi_back/src/main/resources/static",
  }, // 빌드 결과물이 생성되는 경로
  server: {
    proxy: {
      "/members": "http://localhost:8080",
    }, // proxy 설정
  },
});
