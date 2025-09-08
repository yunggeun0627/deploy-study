import { useQuery, useQueryClient } from '@tanstack/react-query';
import React, { useState } from 'react';
import api from '../api/axios';
import { useNavigate } from 'react-router-dom';

function Mypage(props) {
    const queryClient = useQueryClient();
    const navigate = useNavigate();
    const [userId, setUserId] = useState(0);
    const [inputValue, setInputValue] = useState("");

    const user = useQuery({
        queryKey: ["user", userId],
        queryFn: async () => await api.get("/api/user", {params:{id: userId}}),
        enabled: !!userId,
        retry: 0,
    })

    const handleInputOnChange = e => {
        setInputValue(e.target.value);
    }

    const handleInputOnEnter = e => {
        if (e.keyCode === 13) {
            console.log(inputValue)
            setUserId(parseInt(inputValue));
        }
    }

    const handleLogoutOnClick = async () => {
        localStorage.removeItem("AccessToken");
        await queryClient.invalidateQueries({
            queryKey: ["principal"],
        });
        navigate("/");
    }

    return (
        <>
            <div>
                <input type="text" placeholder='user-id' value={inputValue} onChange={handleInputOnChange} onKeyDown={handleInputOnEnter} />
            </div>
            {
                user.isLoading 
                ?   <div>로딩중...</div>
                :   <div>
                        {
                            !!user?.data?.data 
                            ? <h1>email: {user.data.data.email}</h1>
                            : <h1>해당 사용자 정보를 찾을 수 없습니다.</h1>
                        }
                    </div>
            }
            <button onClick={handleLogoutOnClick}>로그아웃</button>
        </>
    );
}

export default Mypage;