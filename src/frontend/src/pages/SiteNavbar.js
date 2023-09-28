import {useEffect, useState} from "react";
import {Button, Menu} from "antd";
import {Link, useNavigate} from "react-router-dom";
import {Header} from "antd/es/layout/layout";

export function SiteNavbar() {
    const [isLoggedIn, setIsLoggedIn] = useState(localStorage.getItem("jwt"));
    const navigate = useNavigate();

    useEffect(() => {
        setIsLoggedIn(localStorage.getItem("jwt"));
    }, [localStorage.getItem("jwt")]);

    function handleLogout() {
        localStorage.removeItem('jwt');
        navigate('/login');

    }

    return (
        <>
            <Header
                style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}
            >
                <div />
                <Menu theme="dark" mode="inline">
                    <div className="button-group">
                        {isLoggedIn ? (
                            <>
                                <Link to="/profile" key="profile-link">
                                    <Button type="primary" shape="round">
                                        Profile
                                    </Button>
                                </Link>
                                <Link to="/meals" key="meals-link">
                                    <Button type="primary" shape="round">
                                        Meals
                                    </Button>
                                </Link>
                                <Button type="primary" shape="round" onClick={handleLogout}>
                                    Logout
                                </Button>
                            </>
                        ) : (
                            <Link to="/login">
                                <Button type="primary" shape="round">
                                    Login
                                </Button>
                            </Link>
                        )}
                    </div>
                </Menu>
            </Header>
        </>
    )
}