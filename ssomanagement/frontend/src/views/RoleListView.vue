<script lang="ts" setup>
import { ref } from 'vue'
import { Edit, Plus } from '@element-plus/icons-vue'
import { RouterLink } from 'vue-router'
import { getRoleList } from '@/api/role'

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

const roleListDummy = getRoleList(0, 10);
</script>

<template>
  <el-row>
    <div >
      <el-button type="primary" :icon="Plus">
        Add Role
      </el-button>
    </div>
  </el-row>
  <el-row v-for="item in roleList">
    <el-col :span="2">
      <el-button type="primary" :icon="Edit" plain>
        Edit
      </el-button>
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