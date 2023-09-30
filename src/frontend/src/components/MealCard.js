import React, {useRef, useState} from 'react';
import { Card, Button } from 'antd';

const MealCard = ({meal, onDelete,onEdit }) => {

    const cardRef = useRef(null);
    const [isOverflowing, setIsOverflowing] = useState(false);

    const handleDelete = () => {
        onDelete(meal, meal.mealDate);
    };
    const handleEdit = () => {
        onEdit(meal,meal.mealDate);
    }


    return (
        <Card
            title={meal.mealName}
            style={{
                width: '100%',
                backgroundColor: 'lightgray',
                height: isOverflowing ? 'auto' : '400px',
                maxHeight: '400px',
                overflowY: 'auto',
            }}
            ref={cardRef}
        >
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
                <Button type="primary" className="delete-button" onClick={handleDelete}>
                    Delete meal
                </Button>
                <Button type="primary" className="delete-button" onClick={handleEdit}>
                    Edit meal
                </Button>
            </div>
        </Card>
    );
};

export default MealCard;
