import { useQueryClient } from '@tanstack/react-query';
import React, { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';

function Oauth2Login(props) {
    const [searchParams] = useSearchParams();
    const queryClient = useQueryClient();
    const navigate = useNavigate();

    useEffect(() => {
        const accessToken = searchParams.get("accessToken");
        localStorage.setItem("AccessToken", "Bearer " + accessToken);
        queryClient.invalidateQueries({
            queryKey: ["principal"],
        }).then(() => {
            navigate("/");
        });
    }, []);

    return (
        <div>
            
        </div>
    );
}

export default Oauth2Login;