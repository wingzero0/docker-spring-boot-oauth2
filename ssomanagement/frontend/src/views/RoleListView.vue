<script lang="ts" setup>
import { ref } from 'vue'
import { Edit } from '@element-plus/icons-vue'
import { RouterLink } from 'vue-router'

const roleList = ref([{
  registeredClientId: "message_client_ui",
  username: "username",
  rolename: "role name",
}, {
  registeredClientId: "message_client_ui",
  username: "username",
  rolename: "role name",
}, {
  registeredClientId: "message_client_ui",
  username: "username",
  rolename: "role name",
}, {
  registeredClientId: "message_client_ui",
  username: "username",
  rolename: "role name",
}, {
  registeredClientId: "message_client_ui",
  username: "username",
  rolename: "role name",
}])

// const roleListDummy = getroleList(0, 10);
</script>

<template>
  <el-row v-for="item in roleList">
    <el-col :span="2">
      <RouterLink :to="{ name: 'applist' }">
        <el-icon>
          <Edit />
        </el-icon>
        Edit
      </RouterLink>
    </el-col>
    <el-col :span="4">
      {{ item.registeredClientId }}
    </el-col>
    <el-col :span="4">
      {{ item.username }}
    </el-col>
    <el-col :span="4">
      {{ item.rolename }}
    </el-col>
  </el-row>
</template>

<style></style>