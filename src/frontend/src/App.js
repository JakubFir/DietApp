import './App.css';
import {Layout} from "antd";
import {Route, Routes} from "react-router-dom";
import {SiteNavbar} from "./pages/SiteNavbar";
import LoginPage from "./pages/LoginPage";
import ProfilePage from "./pages/ProfilePage";
import MealDiaryPage from "./pages/MealDiaryPage";


const {Content} = Layout;


function App() {
    return (
        <Layout>
            <SiteNavbar/>
            <Content>
                <Routes>
                    <Route path="/login" element={<LoginPage/>}/>
                    <Route path="/profile" element={<ProfilePage/>}/>
                    <Route path="/meals" element={<MealDiaryPage/>}/>
                </Routes>
            </Content>
        </Layout>
    );
}

export default App;
