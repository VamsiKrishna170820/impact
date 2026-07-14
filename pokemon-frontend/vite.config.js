import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],

  server: {
    proxy: {
      "/attack": {
        target: "http://localhost:8080",
        changeOrigin: true,
        secure: false
      },
      "/pokemons": {
        target: "http://localhost:8080",
        changeOrigin: true,
        secure: false,
      },
    }
  },

  test: {
    globals: true,
    environment: "jsdom",
    setupFiles: "./src/setupTests.js"
  }


})
