<template>
    <div id="body">
      <h1>用户管理</h1>
      <form @submit.prevent="addUser">
        <span>添加用户：</span>
        <input v-model="user.name" placeholder="Name" required />
        <input v-model="user.email" placeholder="Email" required />
        <input v-model="user.age" type="number" placeholder="Age" />
        <button type="submit">{{ user.id ? '更新用户' : '创建用户' }}</button>
      </form>
      <span>搜索用户：</span>
      <input v-model="search" placeholder="搜索用户" @input="fetchUsers" />
      <div v-if="isEditing">
        <h2>{{ editingUser ? '编辑用户' : '新增用户' }}</h2>
        <input v-model="editingUser.name" placeholder="姓名" />
        <input v-model="editingUser.email" placeholder="邮箱" />
        <input v-model="editingUser.age" placeholder="年龄" />
        <button @click="saveUser">保存</button>
      </div>
      <div id="users">
      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>姓名</th>
            <th>邮箱</th>
            <th>年龄</th>
            <th>操作</th>
          </tr>
        </thead>
        <tbody>
          
          <tr v-for="user in users" :key="user.id">
            <td>{{ user.id }}</td>
            <td>{{ user.name }}</td>
            <td>{{ user.email }}</td>
            <td>{{ user.age }}</td>
            <td>
              <button @click="editUser(user)">编辑</button>
              <button @click="deleteUser(user.id)">删除</button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        users: [],
        user:{
          name: '',
          email: '',
          age: null,
        }
      };
    },
    methods: {
        fetchUsers() {
      axios.get(`http://localhost:8080/api/user`)
        .then(response => {
          this.users= response.data;
        });
    },
      addUser() {
        axios.post('http://localhost:8080/api/user', this.user)
          .then(() => {
            this.fetchUsers();
            this.user = { name: '', email: '', age: null };
          });
      },
      saveUser() {
        if (this.editingUser.id) {
          axios.put(`http://localhost:8080/api/user/${this.editingUser.id}`, this.editingUser)
            .then(() => {
              this.isEditing = false;
              this.fetchUsers();
            });
        } else {
          axios.post('http://localhost:8080/api/user', this.editingUser)
            .then(() => {
              this.isEditing = false;
              this.fetchUsers();
            });
        }
      },
      deleteUser(id) {
        axios.delete(`http://localhost:8080/api/user/${id}`)
          .then(() => {
            this.fetchUsers();
          });
      },
    },
    mounted() {
      this.fetchUsers();
    },
  };
  </script>
  
  <style>

  </style>
  