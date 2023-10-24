<template>
  <v-sheet class="mx-auto px-6 py-15" max-width="450">
    <div class="login-wrapper">
      <h1>
        TABIHOUDAI
      </h1>
      <h2>
        올해 여행 계획도 타비호다이에서 !!!
      </h2>
    </div>

    <v-form
        v-model="form"
        @submit.prevent="onSubmit"
    >
      <v-text-field
          v-model="state.form.email"
          type="email"
          :readonly="loading"
          :rules="[loginEmailRules, checkEmailRule]"
          class="mb-1"
          clearable
          label="이메일"
          placeholder="이메일을 입력하세요."
      ></v-text-field>

      <v-text-field
          v-model="state.form.password"
          type="password"
          :readonly="loading"
          :rules="[loginPasswordRules, checkPasswordRule]"
          :type="show ? 'text' : 'password'"
          clearable
          label="비밀번호"
          placeholder="비밀번호를 입력하세요."
      ></v-text-field>

      <div v-if="error" class="error-message text-center">{{ error }}</div>

      <br>
      <v-btn
          :disabled="!form"
          :loading="loading"
          block
          color="success"
          size="large"
          @click="submit()"
          variant="elevated"
          type="submit"
      >
        로그인
      </v-btn>
      <div class="text-center py-3">
        <router-link to="register">회원가입</router-link>
      </div>

      <div class="text-center">
        <router-link to="pwInquiry">비밀번호가 기억나지 않아요</router-link>
      </div>


    </v-form>
  </v-sheet>


</template>
<style>

.login-wrapper > h1 {
  font-size: 45px;
  color: #000;
  text-align: center;
}

.login-wrapper > h2 {
  font-size: 15px;
  color: #6BAF27;
  margin-bottom: 20px;
  text-align: center;
}

.error-message {
  color: red;
  margin-bottom: auto;
}

</style>
<script>
import {reactive, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

export default {
  setup() {
    const state = reactive({
      form: {
        email: "",
        password: ""
      }
    })

    const router = useRouter();
    const error = ref(null);

    const checkEmailRule = (value) => {
      if (!/.+@.+\..+/.test(value)) return "";
      return true;
    };

    const checkPasswordRule = (value) => {
      if (value.length < 8) return "";
      return true;
    };

    const submit = () => {
      axios.post("members/login", state.form).then((res) => {
        console.log(res);
        router.push('/');
      })
          .catch((errorResponse) => {
            error.value = "로그인 정보가 올바르지 않습니다. 다시 시도하세요.";
            state.form.email = "";
            state.form.password = "";
            console.error(errorResponse);
          })
    }
    return {state, submit, error, checkEmailRule, checkPasswordRule}
  },
}
</script>
