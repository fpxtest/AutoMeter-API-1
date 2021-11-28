/** When your routing table is too long, you can split it into small modules **/

import Layout from '../../views/layout/Layout'

const nestedRouterSubCondition = {
  path: '/condition',
  component: Layout,
  name: '条件管理',
  alwaysShow: true,
  activeMenu: '/condition',
  icon: 'condition',
  children: [
    {
      path: 'condition/list',
      component: () => import('../../views/condition/condition/index'),
      name: '主条件',
      meta: { title: '主条件' }
    },
    {
      path: '/condition/subcondition',
      component: () => import('../../views/condition/apicondition/index'),
      name: '子条件',
      children: [
        {
          path: 'apicondition/list',
          component: () => import('../../views/condition/apicondition/index'),
          name: '接口条件',
          meta: { title: '接口条件' }
        },
        {
          path: 'scriptcondition/list',
          component: () => import('../../views/condition/scriptcondition/index'),
          name: '脚本条件',
          meta: { title: '脚本条件' }
        },
        {
          path: 'dbcondition/list',
          component: () => import('../../views/condition/dbcondition/index'),
          name: '数据库条件',
          meta: { title: '数据库条件' }
        }
      ]
    }
  ]
}
export default nestedRouterSubCondition
