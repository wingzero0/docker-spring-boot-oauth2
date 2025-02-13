import { fileURLToPath, URL } from 'node:url'
import type { UserConfig, ConfigEnv } from 'vite'
import { defineConfig, loadEnv } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'

// https://vite.dev/config/
export default defineConfig(({ mode }: ConfigEnv): UserConfig => {
  const env = loadEnv(mode, process.cwd())
  return {
    plugins: [
      vue(),
      vueDevTools(),
    ],
    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url))
      },
    },
    build: {
      outDir: '../src/main/resources/static/',
      emptyOutDir: true
    },
    server: {
      host: '0.0.0.0',
      proxy: {
        '^/api': {
          target: 'http://localhost:8080/',
          ws: true,
          changeOrigin: true,
          autoRewrite: true,
          cookieDomainRewrite: {
            '*': '127.0.0.1'
          }
        }
      }
    },
    base: env.VITE_WEB_ROOT
  }
})
