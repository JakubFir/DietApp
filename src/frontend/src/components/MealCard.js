import React from 'react';
import { Card, Button } from 'antd';

const MealCard = ({meal, onDelete }) => {
    const handleDelete = () => {
        onDelete(meal, meal.mealDate);
    };

    return (
        <Card title={meal.mealName} style={{
            width: '100%',
            backgroundColor: 'lightgray',
            height: '400px',
            overflowY: 'auto',
        }}>
            <p>Calories: {Math.round(meal.calories)}</p>
            <p>Fat: {Math.round(meal.fat)}</p>
            <p>Protein: {Math.round(meal.protein)}</p>
            <p>Carbs: {Math.round(meal.carbs)}</p>
            <ul>
                {meal.ingredientsList.map((ingredient, ingredientIndex) => (
                    <li key={ingredientIndex}>
                        {ingredient.name} - Calories: {ingredient.calories}
                    </li>
                ))}
            </ul>
            <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                <Button type="primary" style={{ flex: 1 }} onClick={handleDelete}>
                    Delete meal
                </Button>
            </div>
        </Card>
    );
};

export default MealCard;
