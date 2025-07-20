<template>
  <div class="container mt-4">
    <h1 class="mb-4">게시글 목록</h1>

    <div v-if="posts.length === 0" class="alert alert-info" role="alert">
      게시글이 없습니다. 새로운 게시글을 작성해보세요!
    </div>

    <div v-else>
      <div v-for="post in posts" :key="post.id" class="card mb-3">
        <div class="card-body">
          <h5 class="card-title">{{ post.title }}</h5>
<!--          <p class="card-text">{{ post.content }}</p>-->
          <!-- 필요하다면 여기에 더 많은 정보나 버튼을 추가할 수 있습니다. -->
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

// 게시글 데이터를 저장할 반응형 변수 선언
let posts = ref([]);

// 컴포넌트가 마운트(화면에 표시)된 후 실행될 로직
onMounted(async () => {
  try {
    // http://localhost:8080/posts 로 GET 요청을 보냅니다.
    const response = await axios.get('http://localhost:8080/posts?page=1&size=5');
    console.log('axios 응답 전체:', response);
    // 응답으로 받은 데이터를 posts 반응형 변수에 할당합니다.
    posts.value = response.data;
    console.log('게시글 데이터 (posts.value): ', posts.value);
    console.log('posts (Ref 객체): ', posts);
  } catch (error) {
    console.error('게시글을 불러오는 데 실패했습니다:', error);
    alert('게시글을 불러오는 데 실패했습니다. 서버 상태를 확인해주세요.');
  }
});
</script>

<style scoped>
/* 이 컴포넌트에만 적용되는 스타일 */
.container {
  max-width: 900px;
}
</style>

