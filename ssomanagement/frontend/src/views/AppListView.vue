<script lang="ts" setup>
import { ref } from 'vue'
import { Edit } from '@element-plus/icons-vue'
import { RouterLink } from 'vue-router'

const appList = ref([{
  registeredClientId: "message_client_ui",
  clientName: "message client ui",
}, {
  registeredClientId: "message_client_ui",
  clientName: "message client ui",
}, {
  registeredClientId: "message_client_ui",
  clientName: "message client ui",
}, {
  registeredClientId: "message_client_ui",
  clientName: "message client ui",
}, {
  registeredClientId: "message_client_ui",
  clientName: "message client ui",
}])
</script>

<template>
  <el-row v-for="item in appList">
    <el-col :span="2">
      <RouterLink :to="{ name: 'home' }">
        <el-icon>
          <Edit />
        </el-icon>
        Role
      </RouterLink>
    </el-col>
    <el-col :span="4">
      {{ item.registeredClientId }}
    </el-col>
    <el-col :span="4">
      {{ item.clientName }}
    </el-col>
  </el-row>
</template>

<style></style>