import React from 'react';

function InputComponent({ inputProp }) {
  return (
    <div className="flex flex-col gap-1 flex-1 w-full mt-2">
      <label htmlFor={inputProp.field} className="block text-black">
        {inputProp.label}
      </label>
      <input
        placeholder={inputProp.placeholder}
        type={inputProp.type}
        id={inputProp.field}
        className="flex w-full border border-blue-500 p-3 rounded-md focus:border-yellow-400 outline-none"
        name={inputProp.field}
        required
        value={inputProp.value}
        onChange={inputProp.onChange}
      />
    </div>
  );
}

export default InputComponent;