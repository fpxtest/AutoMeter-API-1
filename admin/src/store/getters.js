const getters = {
  sidebar: state => state.app.sidebar,
  visitedViews: state => state.tagsView.visitedViews,
  cachedViews: state => state.tagsView.cachedViews,
  token: state => state.account.token,
  accountId: state => state.account.accountId,
  email: state => state.account.email,
  name: state => state.account.name,
  nickname: state => state.account.nickname,
  loginTime: state => state.account.loginTime,
  registerTime: state => state.account.registerTime,
  roleName: state => state.account.roleName,
  projectlist: state => state.project.projectlist,
  projectid: state => state.project.projectid,
  pname: state => state.project.projectname,
  permissionCodeList: state => state.account.permissionCodeList,

  permissionRouters: state => state.permission.routers,
  addRouters: state => state.permission.addRouters
}
export default getters
