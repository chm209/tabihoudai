<template>
  <v-sheet class="mx-auto px-6 py-8" max-width="450">
    <v-form v-model="form" @submit.prevent="onsubmit">

      <div class="text-subtitle-1 text-medium-emphasis">이메일</div>
      <div class="d-flex align-right">
        <v-text-field v-model="registemail" :readonly="loading" :rules="registemailRules" class="mb-2" clearable
          placeholder="example@example.com"></v-text-field>
        <v-col cols="auto">
          <v-btn @click="checkemail" color="#6BAF27">중복 확인</v-btn>
        </v-col>
      </div>


      <div class="text-subtitle-1 text-medium-emphasis">비밀번호</div>
      <v-text-field v-model="registpassword" id="password" :readonly="loading" :rules="registpasswordRules"
        :type="show ? 'text' : 'password'" clearable required placeholder="비밀번호 입력(영문 대문자, 특수문자 포함) 8~20자"></v-text-field>
      <div class="text-subtitle-1 text-medium-emphasis">비밀번호 재확인</div>

      <v-text-field v-model="conformPassword" :readonly="loading" :rules="conformPasswordRules"
        :type="show ? 'text' : 'password'" clearable required :error-messages="conformPasswordError" class="text-right"
        placeholder="비밀번호 입력(영문 대문자, 특수문자 포함) 8~20자"></v-text-field>


      <div class="text-subtitle-1 text-medium-emphasis">닉네임</div>

      <div class="d-flex align-right">
        <v-text-field v-model="registnickname" :readonly="loading" :rules="registnicknameRules" class="mb-2" clearable
          placeholder="닉네임 입력 2~15자"></v-text-field>
        <v-col cols="auto">
          <v-btn @click="checknickname" color="#6BAF27">중복 확인</v-btn>
        </v-col>
      </div>

      <div class="text-subtitle-1 text-medium-emphasis">생년월일</div>
      <div class="d-flex align-right">
        <v-text-field v-model="registbirthday" :readonly="loading" :rules="registbirthdayRules" class="mb-2" clearable
          placeholder="생년월일은 8자리 (ex.19990101)"></v-text-field>
      </div>
      <br>

      <v-btn :disabled="!form" :loading="loading" block color="success" size="large" type="submit" @click="register"
        variant="elevated">
        회원가입
      </v-btn>
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
          window.alert("회원가입 성공");
          router.push('/login');
        })
        .catch(error => {
          window.alert("회원가입 실패");
        });
    },
    checkemail() {
      // 중복 확인 API 호출
      axios.get(`/members/check-email/${this.registemail}`)
        .then(response => {
          if (response.data === "사용 가능한 이메일입니다.") {
            if (this.$refs.registemail) {
              this.$refs.registemail.validate(); // 이메일 유효성 검사를 통과시킵니다.
            }
            window.alert("사용 가능한 이메일입니다.");
          } else {
            if (this.$refs.registemail) {
              this.$refs.registemail.reset(); // 이메일 값을 초기화합니다.
            }
            window.alert("이미 사용중인 이메일입니다.");
          }
        })
        .catch(error => {
          console.error(error);
        });
    },
    checknickname() {
      // 중복 확인 API 호출
      axios.get(`/members/check-nickname/${this.registnickname}`)
        .then(response => {
          if (response.data === "사용 가능한 닉네임입니다.") {
            if (this.$refs.registnickname) {
              this.$refs.registnickname.validate(); // 이메일 유효성 검사를 통과시킵니다.
            }
            window.alert("사용 가능한 닉네임입니다.");
          } else {
            if (this.$refs.registnickname) {
              this.$refs.registnickname.reset(); // 이메일 값을 초기화합니다.
            }
            window.alert("이미 사용중인 닉네임입니다.");
          }
        })
        .catch(error => {
          console.error(error);
        });
    }
  }
}
</script>