// Vue에서 createApp 함수를 가져옵니다.
import { createApp } from 'vue'
// 최상위 컴포넌트인 App.vue를 가져옵니다.
import App from './App.vue'
// 라우터 설정을 가져옵니다.
import router from './router'

// Bootstrap과 BootstrapVue3를 가져옵니다.
import BootstrapVue3 from 'bootstrap-vue-3'

// Bootstrap과 BootstrapVue3의 CSS 파일을 가져옵니다.
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue-3/dist/bootstrap-vue-3.css'

// 서버로 http 통신을 보내기 위해서 axios를 설치하고 사용하도록 설정

// Vue 앱 인스턴스를 생성합니다.
const app = createApp(App)

// 라우터를 사용하도록 설정합니다.
app.use(router)
// BootstrapVue3를 사용하도록 설정합니다.
app.use(BootstrapVue3)

// 앱을 public/index.html의 #app 요소에 마운트합니다.
app.mount('#app')
