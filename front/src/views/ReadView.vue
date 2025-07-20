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
  max-width: 800px;
}
</style>
