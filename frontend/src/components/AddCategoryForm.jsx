import { useState } from "react";
import Input from "./Input";

const AddCategoryForm = () => {
    const [category, setCategory] = useState({
        name: "",
        type: "income",
        icon: ""
    })

    const categoryTypesOptions = [
        {value: "income", label: "Income"},
        {value: "expense", label: "Expense"},
    ]

    const handleChange = (key, value) => {
        setCategory({...category, [key]: value})
    }
    return (
        <div className="p-4">
            <Input
                value={category.name}
                onChange={(e) => handleChange("name", target.value)}
                label = "Category Name"
                placeholder="e.g., Freelance, Salary, Groceries"
                type="text"
            />

            <Input
                label="Category Type"
                value={category.type}
                onChange={({target}) => handleChange("type", target.value)}
                isSelect={true}
                options={categoryTypesOptions}
            />
        </div>
    )
}

export default AddCategoryForm;