import { Button, Col, Drawer, Form, Input, Row, Select, Space } from "antd";
import { useState } from "react";
import {addMealToUserMealDiary} from "../clients/MealDiaryClient";

const AddMealDrawer = ({ visible, close, selectedDate, updateMeals}) => {
    const [mealName, setMealName] = useState("");
    const [ingredients, setIngredients] = useState([]);



    const handleAddIngredient = () => {
        setIngredients([...ingredients, { name: "", weight: "" }]);
    };

    const handleIngredientChange = (index, fieldName, value) => {
        const updatedIngredients = [...ingredients];
        updatedIngredients[index][fieldName] = value;
        setIngredients(updatedIngredients);
    };

    const formatDateToAPIFormat = (date) => {
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    };

    const handleRemoveIngredient = (index) => {
        const updatedIngredients = [...ingredients];
        updatedIngredients.splice(index, 1);
        setIngredients(updatedIngredients);
    };

    function handleSubmit () {
        const jwtToken = localStorage.getItem("jwt");
        const decodedToken = JSON.parse(atob(jwtToken.split('.')[1]));
        const requestBody = {
            mealName: mealName,
            mealDate: formatDateToAPIFormat(selectedDate),
            ingredientsList: ingredients,
        };
        addMealToUserMealDiary(decodedToken.UserId, requestBody)
            .then((data) => {
                console.log("API response:", data);
                updateMeals(selectedDate);
            })
            .catch((error) => {
                console.error("API error:", error);
            });
        close();
    }

    return (
        <>
            <Drawer
                title="Add a New Meal"
                width={720}
                onClose={close}
                visible={visible}
                bodyStyle={{ paddingBottom: 80 }}
                extra={
                    <Space>
                        <Button onClick={close}>Cancel</Button>
                        <Button type="primary" onClick={handleSubmit}>
                            Submit
                        </Button>
                    </Space>
                }
            >
                <Form layout="vertical" hideRequiredMark>
                    <Row gutter={16}>
                        <Col span={24}>
                            <Form.Item
                                name="mealName"
                                label="Meal Name"
                                rules={[{ required: true, message: "Please enter meal name" }]}
                            >
                                <Input
                                    value={mealName}
                                    onChange={(e) => setMealName(e.target.value)}
                                />
                            </Form.Item>
                        </Col>
                    </Row>
                </Form>
                <Form layout="vertical" hideRequiredMark>
                    {ingredients.map((ingredient, index) => (
                        <Row gutter={16} key={index}>
                            <Col span={8}>
                                <Form.Item
                                    label="Ingredient Name"
                                    required
                                    key={`ingredient-name-${index}`}
                                >
                                    <Input
                                        value={ingredient.name}
                                        onChange={(e) =>
                                            handleIngredientChange(index, "name", e.target.value)
                                        }
                                    />
                                </Form.Item>
                            </Col>
                            <Col span={8}>
                                <Form.Item
                                    label="Weight"
                                    required
                                    key={`ingredient-weight-${index}`}
                                >
                                    <Input
                                        value={ingredient.weight}
                                        onChange={(e) =>
                                            handleIngredientChange(index, "weight", e.target.value)
                                        }
                                    />
                                </Form.Item>
                            </Col>
                            <Col span={8}>
                                <Button
                                    type="default"
                                    onClick={() => handleRemoveIngredient(index)}
                                >
                                    Remove
                                </Button>
                            </Col>
                        </Row>
                    ))}
                    <Row gutter={16}>
                        <Col span={24}>
                            <Button type="dashed" onClick={handleAddIngredient}>
                                Add Ingredient
                            </Button>
                        </Col>
                    </Row>
                </Form>
            </Drawer>
        </>
    );
};

export default AddMealDrawer;
