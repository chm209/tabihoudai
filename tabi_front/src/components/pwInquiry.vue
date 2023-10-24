<template>
  <v-sheet class="mx-auto px-6 py-8" max-width="450">
    <div class="pwinquriy-wrapper">
      <h1>
        TABIHOUDAI
      </h1>
      <h2>
        비밀번호 찾기
      </h2>
      <div class="pwinquriy-line">
        <v-divider
            :thickness="5"
            class="border-opacity-100"
            color: #6BAF27;
        ></v-divider>
      </div>

    </div>

    <v-form
        v-model="form"
        @submit.prevent="onSubmit"
    >

      <div class="text-subtitle-1 text-medium-emphasis">이메일</div>
      <div class="d-flex align-right">
        <v-text-field
            v-model="pwinquiry"
            :readonly="loading"
            :rules="pwinquiryRules"
            class="mb-2"
            clearable
            placeholder="example@gmail.com"
        ></v-text-field>
      </div>
      <v-btn
          :disabled="!form"
          :loading="loading"
          block
          color="success"
          size="large"
          type="submit"
          variant="elevated"
      >
        비밀번호 찾기
      </v-btn>
    </v-form>
  </v-sheet>

  <v-breadcrumbs :items="items">
    <template v-slot:title="{ item }">
      {{ item.title.toUpperCase() }}
    </template>
  </v-breadcrumbs>
</template>

<style>
.mb-2 {
  max-width: 400px;
}

.pwinquriy-wrapper > h1 {
  font-size: 45px;
  color: #000;
  margin-bottom: 10px;
  text-align: center;
}

.pwinquriy-wrapper > h2 {
  font-size: 20px;
  color: #6BAF27;
  margin-bottom: 15px;
  text-align: center;
}

.pwinquriy-line {
  color: #6BAF27;
  margin-bottom: 20px;
}
</style>

<script>
export default {
  data: () => ({
    form: false,
    pwinquiry: null,

    pwinquiry: '',
    pwinquiryRules: [
      value => {
        if (value) return true
        return ''
      },
      value => {
        if (/.+@.+\..+/.test(value)) return true

        return ''
      },
    ],
  }),
}
</script>