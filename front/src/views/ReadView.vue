<template>
  <div class="container mt-4">
    <div v-if="post">
      <h2 class="mb-3">{{ post.title }}</h2>
      <hr>
      <div class="card card-body mb-4">
        <p class="card-text">{{ post.content }}</p>
      </div>
      <button class="btn btn-secondary" @click="goEdit">수정하기</button>
      <button class="btn btn-secondary" @click="goList">목록으로</button>
    </div>
<!--    <div v-else class="alert alert-warning" role="alert">-->
<!--      게시글을 찾을 수 없거나 불러오는 데 실패했습니다.-->
<!--    </div>-->
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const router = useRouter();

const post = ref(null);

onMounted(async () => {
  try {
    const postId = route.params.id;
    if (!postId) {
      // ID가 없으면 게시글을 찾을 수 없다고 처리
      post.value = null;
      return;
    }
    const response = await axios.get(`http://localhost:8080/posts/${postId}`);
    post.value = response.data;
  } catch (err) {
    console.error('게시글을 불러오는 데 실패했습니다:', err);
    // 에러 발생 시 게시글을 찾을 수 없다고 처리
    post.value = null;
  }
});

const goList = () => {
  router.back();
};

const goEdit = () => {
  router.push({ 
    name: 'edit',
    params: { id: route.params.id },
  });
}
</script>

<style scoped>
.container {
  max-width: 760px; /* 디시인사이드 본문 너비와 유사하게 조정 */
  margin: 40px auto; /* 중앙 정렬 및 상하 여백 */
  padding: 0 10px; /* 좌우 패딩 */
  font-family: 'Malgun Gothic', 'Apple SD Gothic Neo', 'Nanum Gothic', sans-serif;
}

h2 {
  font-size: 1.8rem; /* 제목 크기 조정 */
  color: #333;
  margin-bottom: 15px;
  line-height: 1.4;
  font-weight: 600;
}

hr {
  border: none;
  border-top: 1px solid #eee; /* 얇고 깔끔한 구분선 */
  margin: 20px 0;
}

.card {
  border: none; /* 카드 스타일 제거 */
  background-color: transparent; /* 배경 투명 */
  box-shadow: none; /* 그림자 제거 */
}

.card-body {
  padding: 0; /* 패딩 제거 */
}

.card-text {
  font-size: 1rem; /* 내용 폰트 크기 조정 */
  line-height: 1.7; /* 줄 간격 */
  color: #444; /* 텍스트 색상 */
  white-space: pre-wrap; /* 줄바꿈 및 공백 유지 */
  margin-bottom: 30px; /* 내용 하단 여백 */
}

.btn {
  padding: 8px 15px; /* 버튼 패딩 조정 */
  font-size: 0.9rem; /* 버튼 폰트 크기 조정 */
  border-radius: 3px; /* 모서리 둥글기 조정 */
  transition: all 0.1s ease-in-out;
  margin-right: 8px; /* 버튼 간 간격 */
  cursor: pointer;
}

.btn-secondary {
  background-color: #f0f0f0; /* 연한 회색 배경 */
  border: 1px solid #ddd; /* 얇은 경계선 */
  color: #555; /* 어두운 텍스트 */
}

.btn-secondary:hover {
  background-color: #e5e5e5;
  border-color: #ccc;
  color: #333;
}

.btn-secondary:last-child {
  margin-right: 0;
}
</style>
