/** When your routing table is too long, you can split it into small modules **/

import Layout from '../../views/layout/Layout'

const nestedRouterTestManager = {
  path: '/assets',
  component: Layout,
  name: '测试管理',
  alwaysShow: true,
  activeMenu: '/assets',
  icon: 'testmanager',
  children: [
    {
      path: '/cases',
      component: () => import('../../views/assets/cases/apicases/index'), // Parent router-view
      name: '测试用例',
      meta: { title: 'xxxxxx 1' },
      children: [
        {
          path: 'apicases/list',
          component: () => import('../../views/assets/cases/apicases/index'),
          name: '用例库',
          meta: { title: '用例库' }
        }
      ]
    }
    // {
    //   path: '/cases',
    //   component: () => import('../../views/assets/cases/apicasesvariables/index'), // Parent router-view
    //   name: '用例变量',
    //   children: [
    //     {
    //       path: 'apicasesvariables/list',
    //       component: () => import('../../views/assets/cases/apicasesvariables/index'),
    //       name: '用例变量',
    //       meta: { title: '用例变量' }
    //     }
    //   ]
    // }
  ]
}
export default nestedRouterTestManager
