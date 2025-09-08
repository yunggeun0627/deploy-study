/** @jsxImportSource @emotion/react */
import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import { Link, Route, Routes } from 'react-router-dom'
import { css } from '@emotion/react';
import kakaoLoginImg from './assets/kakao_login_medium_wide.png';
import { useQuery } from '@tanstack/react-query'
import api from './api/axios'
import Oauth2Login from './pages/Oauth2Login'
import Mypage from './pages/Mypage'

const kakaoLogin = css`
  display: block;
  margin: 0 auto;
  width: 300px;
  height: 30px;
  background-image: url(${kakaoLoginImg});
  background-position: center;
  background-repeat: no-repeat;
  background-size: contain;
`;


function App() {
  const [count, setCount] = useState(0)
  useEffect(() => {
    const KEY = import.meta.env.VITE_APPLICATION_KEY;
    console.log(KEY);
  }, [])

  const principal = useQuery({
    queryKey: ["principal"],
    queryFn: async () => api.get("/api/principal"),
    retry: 0,
  });

  const DefaultPage = () => (
    <>
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Vite + React</h1>
      <div className="card">
        <button>
          <Link to={"/mypage"}>마이페이지</Link>
        </button>
        <button onClick={() => setCount((count) => count + 1)}>
          count is {count}
        </button>
        <p>
          Edit <code>src/App.jsx</code> and save to test HMR
        </p>
      </div>
      <p className="read-the-docs">
        Click on the Vite and React logos to learn more
      </p>
    </>
  )

  const LoginPage = () => (
    <div className="card">
      <Link css={kakaoLogin} to={"http://localhost:8080/oauth2/authorization/kakao"}></Link>
    </div>
  )

  return (
    principal.isLoading 
      ? <div>로딩중...</div> 
      : principal.isError 
        ? <>
            <Routes>
              <Route path='/' element={<LoginPage />} />
              <Route path='/oauth2/login' element={<Oauth2Login />} />
              <Route path='/*' element={<h1>페이지를 찾을 수 없습니다.</h1>} />
            </Routes>
          </>
        : <>
            <Routes>
              <Route path='/' element={<DefaultPage />} />
              <Route path='/mypage' element={<Mypage />} />
              <Route path='/*' element={<h1>페이지를 찾을 수 없습니다.</h1>} />
            </Routes>
          </>
      
  )
}

export default App
