import SplashPage from './pages/splash/Splash';
import HomePage from './pages/home/Home';
import LoginPage from './pages/auth/Login';
import UsersPage from './pages/users/Users';
import MainTemplate from './pages/MainTemplate';
import CompaniesPage from "./pages/company/Companies";
import CompaniesCreatePage from "./pages/company/CompaniesCreate";
import CompaniesUpdatePage from "./pages/company/CompaniesUpdate";

export const routes = [
  { path: '',
    component: SplashPage,
    meta: {
      title: 'Izicash',
    }
  },
  {
    path: '/auth',
    component: LoginPage,
    name: 'auth',
    meta: {
      title: 'Login',
    }
  },
  {
    component: MainTemplate,
    path: '/app',
    children: [
      {
        path: '/home',
        component: HomePage,
        name: 'home',
        meta: {
          title: 'Home',
        }
      },
      /****Empresas****/
      {
        path: '/companies',
        component: CompaniesPage,
        name: 'companies',
        meta: {
          title: 'Empresas',
        }
      },
      {
        path: '/companies/edit/:id',
        component: CompaniesUpdatePage,
        name: 'companies.edit',
        meta: {
          title: 'Alterar Empresa',
        }
      },
      {
        path: '/companies/create',
        component: CompaniesCreatePage,
        name: 'companies.create',
        meta: {
          title: 'Adicionar Empresa',
        }
      },
      /****Usuários****/
      {
        path: '/users',
        component: UsersPage,
        name: 'users',
        meta: {
          title: 'Usuários',
        }
      }
    ]
  },
];


