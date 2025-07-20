<template>
  <div>
    <p>버튼을 눌러 숫자를 올려보세요.</p>
    <!-- @click="increment"는 버튼을 클릭할 때마다 아래 script에 있는 increment 함수를 실행시킵니다. -->
    <button class="btn btn-primary" @click="increment">클릭!</button>
    <!-- {{ count }}는 count 변수의 현재 값을 화면에 보여줍니다. -->
    <p>현재 숫자: {{ count }}</p>
  </div>
</template>

<script setup>
// Vue에서 반응형 데이터를 만들기 위한 ref 함수를 가져옵니다.
import { ref } from 'vue';

// ref(0)를 사용해서 0으로 초기화된 반응형 변수 'count'를 선언합니다.
// * 'ref'로 감싸야 Vue가 이 값의 변경을 감지!! * 하고 화면을 자동으로 업데이트할 수 있습니다.
const count = ref(0);

// 버튼을 클릭했을 때 실행될 함수입니다.
const increment = () => {
  // ref로 만든 변수의 실제 값에 접근하려면 .value를 사용해야 합니다.
  count.value++;
  console.log('현재 count 값:', count.value);
};
</script>

<style scoped>
div {
  text-align: center;
  padding: 25px;
  background-color: #f8f8f8; /* 배경색 변경 */
  border: 1px solid #eee; /* 얇은 경계선 */
  border-radius: 5px; /* 모서리 둥글기 */
  box-shadow: none; /* 그림자 제거 */
  max-width: 300px; /* 너비 조정 */
  margin: 50px auto;
  font-family: 'Malgun Gothic', 'Apple SD Gothic Neo', 'Nanum Gothic', sans-serif;
}

p {
  font-size: 1rem;
  color: #555;
  margin-bottom: 10px;
}

p:last-of-type {
  font-size: 2rem;
  font-weight: 700;
  color: #333; /* 강조색 대신 일반 텍스트 색상 */
  margin-top: 15px;
}

button {
  padding: 8px 15px;
  font-size: 0.9rem;
  border-radius: 3px;
  background-color: #007bff; /* 강조색 */
  border-color: #007bff;
  color: white;
  cursor: pointer;
  transition: background-color 0.1s ease-in-out, border-color 0.1s ease-in-out;
}

button:hover {
  background-color: #0056b3;
  border-color: #0056b3;
}
</style>
