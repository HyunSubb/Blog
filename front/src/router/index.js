// vue-router에서 createRouter와 createWebHistory 함수를 가져옵니다.
import {createRouter, createWebHistory} from 'vue-router'

// 라우터 인스턴스를 생성합니다.
const router = createRouter({
    // HTML5 History API를 사용하는 히스토리 모드를 설정합니다.
    history: createWebHistory(process.env.BASE_URL),
    // 라우트(경로) 목록을 정의합니다.
    routes: [
        {
            path: '/',       // 루트 경로
            name: 'home',    // 라우트 이름
            // component: HomeView // 해당 경로에 렌더링될 컴포넌트 -> 현재 방식 (Eager Loading): 앱이 시작될 때 모든 페이지 컴포넌트를 한꺼번에 다 불러옵니다.
            // 수정한 방식은 지연 로딩(Lazy Loading) 방식임.
            // 해당 경로에 접근하는 순간에만 컴포넌트 파일을 불러오므로 초기 로딩 속도가 빨라집니다.
            component: () => import('../views/HomeView.vue')
        },
        {
            path: '/count',       // 카운트 예제 경로
            name: 'count',    // 라우트 이름
            component: () => import('../views/PlusCount.vue')
        },
        {
            path: '/write', // '/write' 경로
            name: 'write',  // 라우트 이름
            component: () => import('../views/WriteView.vue')
        }
    ]
})

// 설정이 완료된 라우터 인스턴스를 다른 파일에서 사용할 수 있도록 내보냅니다.
export default router
