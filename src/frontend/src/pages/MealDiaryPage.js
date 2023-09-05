import React, { useState } from 'react';
import { Button, Typography, Card, Space } from 'antd';
import { ArrowLeftOutlined, ArrowRightOutlined } from "@ant-design/icons";
import { getUserMealDiary } from "../clients/MealDiaryClient";
import AddMealDraver from "../drawers/AddMealDraver";

const MealDiaryPage = () => {
    const [selectedDate, setSelectedDate] = useState(new Date());
    const [meals, setMeals] = useState([]);
    const [showDrawer, setShowDrawer] = useState(false)

    const close = async () => {
        setShowDrawer(false);
        await fetchMeals(selectedDate);
    };

    const handleAddMeal = () => {
        setShowDrawer(true);
    }
    const handleDateChange = (date) => {
        setSelectedDate(date);
        fetchMeals(date);
    };

    const formatDateToAPIFormat = (date) => {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    };

    const fetchMeals = (date) => {
        const jwtToken = localStorage.getItem("jwt");
        const decodedToken = JSON.parse(atob(jwtToken.split('.')[1]));
        const formattedDate = formatDateToAPIFormat(date);
        getUserMealDiary(decodedToken.UserId, formattedDate)
            .then(res => res.json())
            .then(data => {
                console.log(data)
                setMeals(data);
            });
    };

    const renderContent = () => {
        if (!Array.isArray(meals.list)) {
            return <div style={{ marginBottom: '10vh', display: 'flex', justifyContent: 'center' }}>
                <Button type="primary" >Add Meal</Button>
            </div>
        }
        return (
            <>
                <AddMealDraver
                    visible={showDrawer}
                    close={close}
                    selectedDate={selectedDate}
                />
            <div>
                <div style={{ marginBottom: '10vh', display: 'flex', justifyContent: 'center' }}>
                    <Button type="primary" onClick={() => handleAddMeal()}>Add Meal </Button>
                </div>
                <Space direction="horizontal">
                    {meals.list.map((meal, index) => (
                        <Card key={index} title={meal.mealName} style={{
                            width: '100%',
                            backgroundColor: 'lightgray',
                            height: '400px',
                            overflowY: 'auto',
                        }}>
                            <p>Calories: {meal.calories}</p>
                            <p>Fat: {meal.fat}</p>
                            <p>Protein: {meal.protein}</p>
                            <p>Carbs: {meal.carbs}</p>
                            <ul>
                                {meal.ingredientsList.map((ingredient, ingredientIndex) => (
                                    <li key={ingredientIndex}>
                                        {ingredient.name} - Calories: {ingredient.calories}
                                    </li>
                                ))}
                            </ul>
                            <Button type={"primary"}>
                                Edit meal
                            </Button>
                        </Card>
                    ))}
                </Space>
            </div>
            </>
        );
    };

    return (
        <div style={{ backgroundColor: 'white', padding: '16px', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: '16px', marginBottom: '16px' }}>
                <Button icon={<ArrowLeftOutlined />} onClick={() => handleDateChange(new Date(selectedDate - 24 * 60 * 60 * 1000))}>
                    Previous Day
                </Button>
                <div className="date-container">
                    <Typography.Title level={3} style={{ margin: 0 }}>{selectedDate.toDateString()}</Typography.Title>
                </div>
                <Button icon={<ArrowRightOutlined />} onClick={() => handleDateChange(new Date(selectedDate.getTime() + 24 * 60 * 60 * 1000))}>
                    Next Day
                </Button>
            </div>
            <div style={{ marginTop: '16px' }}>
                {renderContent()}
            </div>
        </div>
    );
};
export default MealDiaryPage;
