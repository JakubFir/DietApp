import {Button, Col, Drawer, Form, Input, Row, Select, Space} from "antd";
import {useEffect, useState} from "react";
import {addMealToUserMealDiary, updateUserMeal} from "../clients/MealDiaryClient";
import {errorNotification, successNotification} from "../notifications/Notifications";

const AddMealDrawer = ({visible, close, selectedDate, updateMeals, isEdit, mealToEdit}) => {
        const [mealName, setMealName] = useState("");
        const [ingredients, setIngredients] = useState([])



        useEffect(() => {
            if (isEdit) {
                setMealName(mealToEdit.mealName || "");
                setIngredients(mealToEdit.ingredientsList || []);
            }
        }, [isEdit, mealToEdit]);

        const handleAddIngredient = () => {
            setIngredients([...ingredients, {name: "", weight: ""}]);
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

        function handleSubmit() {
            const jwtToken = localStorage.getItem("jwt");
            const decodedToken = JSON.parse(atob(jwtToken.split('.')[1]));
            if (isEdit) {
                const requestBody = {
                    mealName: mealName,
                    mealId: mealToEdit.mealId,
                    mealDate: formatDateToAPIFormat(selectedDate),
                    ingredientsList: ingredients,
                };

                updateUserMeal(decodedToken.UserId, requestBody)
                    .then(() => {
                        updateMeals(selectedDate);
                        close();
                        successNotification("Meal Updated!")
                    })
                    .catch((error) => {
                        error.response.json().then(errorData => {
                            errorNotification(errorData.message);
                        }).catch(() => {
                            error.response.text().then(plainText => {
                                errorNotification(plainText);
                            });
                        });
                    });
            } else {
                const requestBody = {
                    mealName: mealName,
                    mealDate: formatDateToAPIFormat(selectedDate),
                    ingredientsList: ingredients,
                };
                addMealToUserMealDiary(decodedToken.UserId, requestBody)
                    .then(() => {
                        updateMeals(selectedDate);
                        close();
                        successNotification("Meal Added!")
                    })
                    .catch((error) => {
                        error.response.json().then(errorData => {
                            errorNotification(errorData.message);
                        }).catch(() => {
                            error.response.text().then(plainText => {
                                errorNotification(plainText);

                            });
                        });
                    });
            }
        }

        return (
            <>
                <Drawer
                    title="Add a New Meal"
                    width={720}
                    onClose={close}
                    visible={visible}
                    bodyStyle={{paddingBottom: 80}}
                    extra={
                        <Space>
                            <Button onClick={close}>Cancel</Button>
                            <Button type="primary" onClick={handleSubmit}>
                                {isEdit ? "Update" : "Submit"}
                            </Button>
                        </Space>
                    }
                >
                    <Form layout="vertical" hideRequiredMark>
                        <Row gutter={16}>
                            <Col span={24}>
                                <Form.Item
                                    required
                                    label="Meal Name"
                                    rules={[{required: true, message: "Please enter meal name"}]}
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
    }
;

export default AddMealDrawer;
