import { useState } from "react";
import Input from "./Input";
import EmojiPickerPopup from "./EmojiPickerPopup";

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
            <EmojiPickerPopup
                icon={category.icon}
                onSelect={(selectIcon) => handleChange("icon", selectIcon)}
            />

            <Input
                value={category.name}
                onChange={(target) => handleChange("name", target.value)}
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