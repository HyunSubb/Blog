<template>
  <div class="container mt-4" style="max-width: 800px;">
    
    <!-- 제목 입력란 -->
    <div class="mb-3">
      <!-- form-control 클래스로 Bootstrap의 기본 입력 필드 스타일을 적용합니다. -->
      <input type="text" class="form-control" placeholder="제목을 입력해주세요" v-model="title">
    </div>

    <!-- 내용 입력란 -->
    <div class="mb-3">
      <!-- el-input 대신 textarea를 사용하고, form-control 클래스를 적용합니다. -->
      <textarea class="form-control" rows="10" placeholder="내용을 입력해주세요" v-model="content"></textarea>
    </div>

    <!-- 버튼 영역 -->
    <div class="d-flex justify-content-end">
      <!-- btn, btn-primary 클래스는 이미 적용되어 있습니다. -->
      <button class="btn btn-primary" @click="Edit()">글 수정 완료</button>
    </div>

  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const router = useRouter();

// 기능 로직 없이, 순수하게 템플릿의 스타일링만 변경했습니다.
const title = ref('');
const content = ref('');

onMounted(() => {
  axios.get(`http://localhost:8080/posts/${route.params.id}`)
    .then(response => {
      title.value = response.data.title;
      content.value = response.data.content;
    })
    .catch(error => {
      console.error("Error fetching post:", error);
      // Optionally, redirect to an error page or home page
    });
})

const Edit = function() {
  // console.log(title.value + "/" + content.value);
  // alert(title.value + "/" + content.value);
  axios.patch(`http://localhost:8080/posts/${route.params.id}`, {
    title: title.value,
    content: content.value
  }).then(() => {
    router.push({
      name: 'read', // router/index.js에 정의된 상세 페이지 라우트의 이름
      params: { id: route.params.id } // URL 경로에 포함될 파라미터 (예: /posts/123)
    });
  })
}

</script>

<style scoped>
.container {
  max-width: 760px;
  margin: 40px auto;
  padding: 0 10px;
  font-family: 'Malgun Gothic', 'Apple SD Gothic Neo', 'Nanum Gothic', sans-serif;
}

.mb-3 {
  margin-bottom: 15px !important; /* Bootstrap mb-3보다 약간 줄임 */
}

.form-control {
  border: 1px solid #ccc; /* 더 깔끔한 경계선 */
  border-radius: 3px; /* 모서리 둥글기 조정 */
  padding: 10px 12px;
  font-size: 1rem;
  box-shadow: none; /* 그림자 제거 */
  transition: border-color 0.1s ease-in-out;
}

.form-control:focus {
  border-color: #007bff; /* 포커스 시 강조색 */
  box-shadow: none; /* 그림자 제거 */
  outline: none; /* 기본 아웃라인 제거 */
}

textarea.form-control {
  min-height: 250px; /* 높이 조정 */
  resize: vertical;
}

.btn-primary {
  padding: 8px 15px;
  font-size: 0.9rem;
  border-radius: 3px;
  background-color: #007bff; /* 강조색 */
  border-color: #007bff;
  transition: background-color 0.1s ease-in-out, border-color 0.1s ease-in-out;
}

.btn-primary:hover {
  background-color: #0056b3;
  border-color: #0056b3;
}
</style>
