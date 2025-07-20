<template>
  <!-- 
    [App.vue의 역할]
    - 이 컴포넌트는 우리 앱 전체의 "껍데기" 또는 "틀" 역할을 합니다.
    - 모든 페이지에서 공통으로 보여야 하는 상단 메뉴(헤더), 하단 정보(푸터) 등을 이곳에 배치합니다.
    - 아래에 있는 <router-view/> 태그가 바로 "현재 URL에 맞는 페이지 컴포넌트가 그려질 자리"를 의미합니다.
    - 예를 들어, 사용자가 /write 경로로 이동하면, <router-view/> 부분에 WriteView.vue 컴포넌트의 내용이 표시됩니다.
      이때 상단의 Navbar는 그대로 유지됩니다.
  -->

  <!-- Bootstrap Navbar를 적용합니다. -->
  <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
      <!-- 웹사이트 로고/이름. 클릭하면 홈으로 이동합니다. -->
      <router-link class="navbar-brand" to="/">My Blog</router-link>
      
      <!-- 화면이 작아질 때 나타나는 햄버거 버튼 -->
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <!-- 네비게이션 링크들을 감싸는 부분 -->
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item">
            <router-link class="nav-link" to="/">Home</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/write">글 작성</router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>

  <!-- router-view는 현재 URL에 해당하는 컴포넌트를 렌더링합니다. -->
  <router-view/>
</template>

<script>
export default {
  name: 'App',
}
</script>

<style>
/* 전역 스타일을 여기에 정의할 수 있습니다. */
body {
  background-color: #f8f9fa; /* 전체 페이지 배경색을 약간의 회색으로 설정 */
}
</style>
