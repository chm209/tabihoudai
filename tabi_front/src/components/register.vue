<template>
  <v-sheet class="mx-auto px-6 py-8" max-width="450">
    <v-form
        v-model="form"
        @submit.prevent="onsubmit"
    >

      <div class="text-subtitle-1 text-medium-emphasis">이메일</div>
      <div class="d-flex align-right">
        <v-text-field
            v-model="registemail"
            :readonly="loading"
            :rules="registemailRules"
            class="mb-2"
            clearable
            placeholder="example@gmail.com"
        ></v-text-field>
        <v-col cols="auto">
          <v-btn @click="checkEmail" color="#6BAF27">중복 확인</v-btn>
        </v-col>

      </div>


      <div class="text-subtitle-1 text-medium-emphasis">비밀번호</div>
      <v-text-field
          v-model="registpassword"
          id="password"
          :readonly="loading"
          :rules="registpasswordRules"
          :type="show ? 'text' : 'password'"
          clearable
          required
          placeholder="비밀번호 입력 8~20자"
      ></v-text-field>

      <div class="text-subtitle-1 text-medium-emphasis">비밀번호 재확인</div>

      <v-text-field
          v-model="conformPassword"
          :readonly="loading"
          :rules="conformPasswordRules"
          :type="show ? 'text' : 'password'"
          clearable
          required
          :error-messages="conformPasswordError"
          class="text-right"
          placeholder="비밀번호 입력 8~20자"
      ></v-text-field>


      <div class="text-subtitle-1 text-medium-emphasis">닉네임</div>

      <div class="d-flex align-right">
        <v-text-field
            v-model="registnickname"
            :readonly="loading"
            :rules="registnicknameRules"
            class="mb-2"
            clearable
            placeholder="닉네임 입력 2~15자"
        ></v-text-field>
        <v-col cols="auto">
          <v-btn @click="checknickname" color="#6BAF27">중복 확인</v-btn>
        </v-col>
      </div>

      <div class="text-subtitle-1 text-medium-emphasis">생년월일</div>
      <div class="d-flex align-right">
        <v-text-field
            v-model="registbirthday"
            :readonly="loading"
            :rules="registbirthdayRules"
            class="mb-2"
            clearable
            placeholder="생년월일은 8자리 (ex.19990101)"
        ></v-text-field>
      </div>
      <br>

      <v-btn
          :disabled="!form"
          :loading="loading"
          block
          color="success"
          size="large"
          type="submit"
          @click="register"
          variant="elevated"
      >
        회원가입
      </v-btn>
      <v-dialog v-model="registrationSuccess" max-width="300">
        <v-card>
          <v-card-title>회원가입 성공</v-card-title>
          <v-card-actions>
            <v-btn color="primary" @click="goToLogin">로그인 페이지로</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="registrationFalse" max-width="300">
        <v-card>
          <v-card-title>회원가입 실패</v-card-title>
          <v-card-actions>
            <v-btn color="primary" @click="closeDialog">닫기</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-form>
  </v-sheet>
</template>

<style>
.mb-2 {
  max-width: 300px;
}
</style>

<script>
import router from "@/router/router";
import axios from "axios";

export default {
  data() {
    return {
      form: false,
      loading: false,
      email: '',
      password: '',
      nickname: '',
      birthday: '',
      registrationSuccess: false,
      registrationFalse: false,

      registemail: null,
      registpassword: null,
      conformPassword: null,
      registnickname: null,
      registbirthday: null,

      registemail: '',
      registemailRules: [
        value => {
          if (value) return true
          return ''
        },
        value => {
          if (/.+@.+\..+/.test(value)) return true

          return ''
        },
      ],

      registpassword: '',
      registpasswordRules: [
        value => {
          if (value?.length > 7) return true
          return ''
        },
      ],

      conformPassword: '',
      conformpasswordRules: [
        value => {
          if (value?.length > 7) return true
          return ''
        },
      ],

      registnickname: '',
      registnicknameRules: [
        value => {
          if (value?.length > 2) return true
          return ''
        },
      ],

      registbirthday: '',
      registbirthdayRules: [
        value => {
          if (value?.length > 7) return true
          return ''
        },
      ],

    }
  },

  computed: {
    conformPasswordError() {
      return this.conformPassword && this.registpassword !== this.conformPassword ? ['*비밀번호가 일치하지 않습니다.'] : []
    }
  },
  methods: {
    register() {
      // API 호출하여 회원가입 정보 전송
      axios.post('/members/signup', {
        email: this.registemail,
        password: this.registpassword,
        nickname: this.registnickname,
        birthday: this.registbirthday
      })
          .then(response => {
            this.registrationSuccess = true;

          })
          .catch(error => {
            this.registrationFalse = true;
          });
    },
    goToLogin() {
      router.push('/login');
    },
    closeDialog() {
      this.registrationFalse = false;
    },
  }
}
</script>