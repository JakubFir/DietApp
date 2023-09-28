import React, {useState, useEffect} from 'react';
import {Button, Typography, Card, Space, Progress, Tooltip} from 'antd';
import {ArrowLeftOutlined, ArrowRightOutlined} from "@ant-design/icons";
import {getUserMealDiary} from "../clients/MealDiaryClient";
import AddMealDrawer from "../drawers/AddMealDrawer";
import {getUserInformation} from "../clients/UserClient";
import {deleteUserMeal} from "../clients/MealClient";
import MealCard from "../components/MealCard";

const MealDiaryPage = () => {
    const [selectedDate, setSelectedDate] = useState(new Date());
    const [meals, setMeals] = useState([]);
    const [showDrawer, setShowDrawer] = useState(false);
    const [caloricDemandProgress, setCaloricDemandProgress] = useState(0);
    const [dietMacros, setDietMacros] = useState({});
    const [decodedToken, setDecodedToken] = useState(null); // Initialize as null


    const close = () => {
        setShowDrawer(false);
    };

    const handleAddMeal = () => {
        setShowDrawer(true);
    };
    const updateMeals = (date) => {
        fetchMeals(date); //
    };
    const handleDateChange = (date) => {
        setSelectedDate(date);
    };

    const fetchMeals = (date) => {
        const formattedDate = formatDateToAPIFormat(date);
        getUserMealDiary(decodedToken.UserId, formattedDate)
            .then(res => res.json())
            .then(data => {
                setMeals(data);
            });
    };
    const handleDeleteUserMeal = (mealDto) => {
        ;
        deleteUserMeal(decodedToken.UserId, mealDto.mealDate, mealDto)
            .then(() => updateMeals(selectedDate))
            .catch(error => {
                console.log(error);
            });
    };
    const getUserDietMacro = () => {
        getUserInformation(decodedToken.UserId)
            .then(res => res.json())
            .then(data => {
                if (data && data.diet) {
                    let calories = data.caloricDemand;
                    const {protein, fat, carbs} = data.diet;
                    setDietMacros({calories, protein, fat, carbs});
                }
            })
            .catch(error => {
                console.error('Error fetching user diet macro:', error);
            });
    }
    const calculateCaloricDemandProgress = () => {
        if (meals.list && typeof meals.caloricDemand === 'number') {
            let progress = ((meals.caloricDemand - meals.remainingCalories) / meals.caloricDemand) * 100;
            progress = Math.max(progress, 0);
            setCaloricDemandProgress(Math.round(progress));
        }
    };
    useEffect(() => {
        const jwtToken = localStorage.getItem("jwt");
        const decodedToken = JSON.parse(atob(jwtToken.split('.')[1]));
        setDecodedToken(decodedToken);
    }, []);

    useEffect(() => {
        if (decodedToken) {
            fetchMeals(selectedDate);
            getUserDietMacro();
        }
    }, [selectedDate]);

    useEffect(() => {
        calculateCaloricDemandProgress();
    }, [meals]);

    const formatDateToAPIFormat = (date) => {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    };

    const calculateTotalSum = () => {
        if (Array.isArray(meals.list)) {
            const totalSum = meals.list.reduce((acc, meal) => {
                acc.calories += meal.calories || 0;
                acc.fat += meal.fat || 0;
                acc.protein += meal.protein || 0;
                acc.carbs += meal.carbs || 0;
                return acc;
            }, {calories: 0, fat: 0, protein: 0, carbs: 0});

            return totalSum;
        }
        return {calories: 0, fat: 0, protein: 0, carbs: 0};
    };

    const totalSum = calculateTotalSum();

    const renderContent = () => {
        if (!Array.isArray(meals.list)) {
            return (
                <div style={{marginBottom: '10vh', display: 'flex', justifyContent: 'center'}}>
                    <Button type="primary" onClick={handleAddMeal}>Add Meal</Button>
                </div>
            );
        }
        return (
            <>
                <AddMealDrawer
                    visible={showDrawer}
                    close={close}
                    selectedDate={selectedDate}
                    updateMeals={updateMeals}
                />
                <div>
                    <div style={{marginBottom: '5vh', display: 'flex', justifyContent: 'center'}}>
                        <Button type="primary" onClick={handleAddMeal}>Add Meal</Button>
                    </div>

                    <Space direction="horizontal">
                        {meals.list.map((meal, index) => (
                            <MealCard
                                key={index}
                                meal={meal}
                                onDelete={handleDeleteUserMeal}
                            />
                        ))}
                        <Card title="Total Sum" style={{
                            width: '100',
                            backgroundColor: 'lightgray',
                            height: '400px',
                        }}>
                            <p>Calories: {Math.round(totalSum.calories)} /{dietMacros.calories}</p>
                            <p>Fat: {Math.round(totalSum.fat)} / {dietMacros.fat}</p>
                            <p>Protein: {Math.round(totalSum.protein)} / {dietMacros.protein}</p>
                            <p>Carbs: {Math.round(totalSum.carbs)} / {dietMacros.carbs}</p>
                            <Space>
                                <Tooltip
                                    title={`${caloricDemandProgress.toFixed(2)}% of daily caloric demand achieved`}>
                                    <Progress percent={caloricDemandProgress} success={{percent: caloricDemandProgress}}
                                              type="dashboard"/>
                                </Tooltip>
                            </Space>
                        </Card>
                    </Space>
                </div>
            </>
        );
    };

    return (
        <>
            <div style={{
                backgroundColor: 'white',
                padding: '16px',
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center'
            }}>
                <div style={{display: 'flex', alignItems: 'center', gap: '16px', marginBottom: '16px'}}>
                    <Button icon={<ArrowLeftOutlined/>}
                            onClick={() => handleDateChange(new Date(selectedDate - 24 * 60 * 60 * 1000))}>
                        Previous Day
                    </Button>
                    <div className="date-container">
                        <Typography.Title level={3} style={{margin: 0}}>{selectedDate.toDateString()}</Typography.Title>
                    </div>
                    <Button icon={<ArrowRightOutlined/>}
                            onClick={() => handleDateChange(new Date(selectedDate.getTime() + 24 * 60 * 60 * 1000))}>
                        Next Day
                    </Button>
                </div>
                <div style={{marginTop: '16px'}}>
                    {renderContent()}
                </div>
            </div>
        </>
    );
};

export default MealDiaryPage;
